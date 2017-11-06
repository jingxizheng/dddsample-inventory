package com.linesum.inventory.application.impl;

import com.linesum.inventory.BaseJunitTestCase;
import com.linesum.inventory.application.ApplicationEvents;
import com.linesum.inventory.application.StoreService;
import com.linesum.inventory.domain.model.order.*;
import com.linesum.inventory.domain.model.store.*;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.*;

/**
 * Created by zhengjx on 2017/11/6.
 */
public class StoreServiceImplTest extends BaseJunitTestCase {

    @InjectMocks
    private StoreService storeService = new StoreServiceImpl();

    @Mock
    private PhysicalStoreRepository physicalStoreRepository;

    @Mock
    private LogicStoreRepository logicStoreRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ApplicationEvents applicationEvents;

    private final SkuCode skuCode1 = new SkuCode(UUID.randomUUID().toString());
    private final SkuCode skuCode2 = new SkuCode(UUID.randomUUID().toString());

    private final LogicStore.LogicStoreId logicStoreId = new LogicStore.LogicStoreId(1L);

    private final PhysicalStore.PhysicalStoreId physicalStoreId = new PhysicalStore.PhysicalStoreId(2L);

    private final WarehouseId warehouseId = new WarehouseId(3L);

    private final ContactId contactId1 = new ContactId(4L);

    private final ContactId contactId2 = new ContactId(5L);

    private final OrderId orderId = new OrderId(6L);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // mock logicStoreRepository.find
        when(logicStoreRepository.find(logicStoreId))
                .thenReturn(new LogicStore(
                        logicStoreId,
                        Lists.newArrayList(
                                new Goods(skuCode1, 100, new BigDecimal("100.00")),
                                new Goods(skuCode2, 200, new BigDecimal("200.00"))),
                        new PhysicalStore(physicalStoreId, null, null, null)
                ));

        // mock physicalStoreRepository.find
        when(physicalStoreRepository.find(physicalStoreId))
                .thenReturn(new PhysicalStore(
                        physicalStoreId,
                        warehouseId,
                        new WarehouseInfo(
                                new Contact(contactId1, "contact_name_1", "contact_addr_1", "contact_phone_1"),
                                300, 1000
                        ),
                        Lists.newArrayList(
                                new Goods(skuCode1, 100, new BigDecimal("100.00")),
                                new Goods(skuCode2, 200, new BigDecimal("200.00")))
                ));

        // mock contactRepository.find
        when(contactRepository.find(contactId2))
                .thenReturn(new Contact(contactId2, "contact_name_2", "contact_addr_2", "contact_phone_2"));

        // mock logicStoreRepository.save
        when(logicStoreRepository.save(any(LogicStore.class))).thenReturn(logicStoreId);

        // mock physicalStoreRepository.save
        when(physicalStoreRepository.save(any(PhysicalStore.class))).thenReturn(physicalStoreId);

        // mock orderRepository.save
        when(orderRepository.save(any(Order.class)))
                .thenReturn(orderId);

        // mock applicationEvents.transferInPhysicalStore
        doNothing().when(applicationEvents).transferInPhysicalStore(orderId);
    }

    @Test
    public void transferInLogicStore() throws Exception {
        OrderId orderId = storeService.transferInLogicStore(
                Lists.newArrayList(
                        new Goods(skuCode1, 100, new BigDecimal("100.00")),
                        new Goods(skuCode2, 200, new BigDecimal("200.00"))),
                logicStoreId,
                contactId2);

        assertThat(orderId)
                .isNotNull();

        // assert logic store
        ArgumentCaptor<LogicStore> logicStoreArgumentCaptor = ArgumentCaptor.forClass(LogicStore.class);
        verify(logicStoreRepository).save(logicStoreArgumentCaptor.capture());
        LogicStore logicStore = logicStoreArgumentCaptor.getValue();

        assertThat(logicStore.getGoodsList())
                .extracting(goods -> goods.getSkuCode().getCode(), Goods::getQty, Goods::getPrice)
                .contains(tuple(skuCode1.getCode(), 200, new BigDecimal("100.00")),
                        tuple(skuCode2.getCode(), 400, new BigDecimal("200.00")));

        // assert physical store
        ArgumentCaptor<PhysicalStore> physicalStoreArgumentCaptor = ArgumentCaptor.forClass(PhysicalStore.class);
        verify(physicalStoreRepository).save(physicalStoreArgumentCaptor.capture());
        PhysicalStore physicalStore = physicalStoreArgumentCaptor.getValue();

        assertThat(physicalStore.getWarehouseInfo().getUsedCapacity())
                .isEqualTo(600);
        assertThat(physicalStore.getGoodsList())
                .extracting(goods -> goods.getSkuCode().getCode(), Goods::getQty, Goods::getPrice)
                .contains(tuple(skuCode1.getCode(), 200, new BigDecimal("100.00")),
                        tuple(skuCode2.getCode(), 400, new BigDecimal("200.00")));

        // assert order
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order order = orderArgumentCaptor.getValue();

        assertThat(order)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept("orderId", "acceptDate");
        assertThat(order.getAcceptor())
                .extracting("contactId", "name", "address", "telephone")
                .contains(contactId1, "contact_name_1", "contact_addr_1", "contact_phone_1");
        assertThat(order.getSender())
                .extracting("contactId", "name", "address", "telephone")
                .contains(contactId2, "contact_name_2", "contact_addr_2", "contact_phone_2");
        assertThat(order.getOrderGoodsList())
                .isNotNull()
                .isNotEmpty()
                .extracting(goods -> goods.getSkuCode().getCode(), Goods::getQty, Goods::getPrice)
                .contains(tuple(skuCode1.getCode(), 100, new BigDecimal("100.00")),
                        tuple(skuCode2.getCode(), 200, new BigDecimal("200.00")));

        // assert event publish
        verify(applicationEvents, times(1)).transferInPhysicalStore(orderId);
    }

    @Test
    public void transferOutLogicStore() throws Exception {
        OrderId orderId = storeService.transferOutLogicStore(
                Lists.newArrayList(
                        new Goods(skuCode1, 50, new BigDecimal("100.00")),
                        new Goods(skuCode2, 100, new BigDecimal("200.00"))),
                logicStoreId,
                contactId2);

        assertThat(orderId)
                .isNotNull();

        // assert logic store
        ArgumentCaptor<LogicStore> logicStoreArgumentCaptor = ArgumentCaptor.forClass(LogicStore.class);
        verify(logicStoreRepository).save(logicStoreArgumentCaptor.capture());
        LogicStore logicStore = logicStoreArgumentCaptor.getValue();

        assertThat(logicStore.getGoodsList())
                .extracting(goods -> goods.getSkuCode().getCode(), Goods::getQty, Goods::getPrice)
                .contains(tuple(skuCode1.getCode(), 50, new BigDecimal("100.00")),
                        tuple(skuCode2.getCode(), 100, new BigDecimal("200.00")));

        // assert physical store
        ArgumentCaptor<PhysicalStore> physicalStoreArgumentCaptor = ArgumentCaptor.forClass(PhysicalStore.class);
        verify(physicalStoreRepository).save(physicalStoreArgumentCaptor.capture());
        PhysicalStore physicalStore = physicalStoreArgumentCaptor.getValue();

        assertThat(physicalStore.getWarehouseInfo().getUsedCapacity())
                .isEqualTo(150);
        assertThat(physicalStore.getGoodsList())
                .extracting(goods -> goods.getSkuCode().getCode(), Goods::getQty, Goods::getPrice)
                .contains(tuple(skuCode1.getCode(), 50, new BigDecimal("100.00")),
                        tuple(skuCode2.getCode(), 100, new BigDecimal("200.00")));

        // assert order
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order order = orderArgumentCaptor.getValue();

        assertThat(order)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept("orderId", "acceptDate");
        assertThat(order.getSender())
                .extracting("contactId", "name", "address", "telephone")
                .contains(contactId1, "contact_name_1", "contact_addr_1", "contact_phone_1");
        assertThat(order.getAcceptor())
                .extracting("contactId", "name", "address", "telephone")
                .contains(contactId2, "contact_name_2", "contact_addr_2", "contact_phone_2");
        assertThat(order.getOrderGoodsList())
                .isNotNull()
                .isNotEmpty()
                .extracting(goods -> goods.getSkuCode().getCode(), Goods::getQty, Goods::getPrice)
                .contains(tuple(skuCode1.getCode(), 50, new BigDecimal("100.00")),
                        tuple(skuCode2.getCode(), 100, new BigDecimal("200.00")));

        // assert event publish
        verify(applicationEvents, times(1)).transferOutPhysicalStore(orderId);
    }

    @Test
    public void transferLogicStore() throws Exception {
    }

    @Test
    public void findSalesStore() throws Exception {
    }

}