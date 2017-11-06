package com.linesum.inventory.application.impl;

import com.linesum.inventory.BaseJunitTestCase;
import com.linesum.inventory.application.ApplicationEvents;
import com.linesum.inventory.application.StoreService;
import com.linesum.inventory.domain.model.order.*;
import com.linesum.inventory.domain.model.store.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

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

        Mockito.when(logicStoreRepository.find(logicStoreId))
                .thenReturn(new LogicStore(
                        logicStoreId,
                        Lists.newArrayList(
                                new Goods(skuCode1, 100, new BigDecimal("100.00")),
                                new Goods(skuCode2, 200, new BigDecimal("200.00"))),
                        new PhysicalStore(physicalStoreId, null, null, null)
                ));

        Mockito.when(physicalStoreRepository.find(physicalStoreId))
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

        Mockito.when(contactRepository.find(contactId2))
                .thenReturn(new Contact(contactId2, "contact_name_2", "contact_addr_2", "contact_phone_2"));

        Mockito.when(orderRepository.save(Mockito.any(Order.class)))
                .thenReturn(orderId);

        Mockito.doNothing().when(applicationEvents).transferInPhysicalStore(orderId);
    }

    @Test
    public void transferInLogicStore() throws Exception {
        OrderId orderId = storeService.transferInLogicStore(
                Lists.newArrayList(
                        new Goods(skuCode1, 100, new BigDecimal("100.00")),
                        new Goods(skuCode2, 200, new BigDecimal("200.00"))),
                logicStoreId,
                contactId2);

        Assertions.assertThat(orderId).isNotNull();

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        Mockito.verify(orderRepository).save(orderArgumentCaptor.capture());
        Order order = orderArgumentCaptor.getValue();

        Assertions.assertThat(order)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept("orderId", "acceptDate");

        Assertions.assertThat(order.getAcceptor())
                .hasFieldOrPropertyWithValue("contactId", contactId1)
                .hasFieldOrPropertyWithValue("name", "contact_name_1")
                .hasFieldOrPropertyWithValue("address", "contact_addr_1")
                .hasFieldOrPropertyWithValue("telephone", "contact_phone_1");

        Assertions.assertThat(order.getSender())
                .hasFieldOrPropertyWithValue("contactId", contactId2)
                .hasFieldOrPropertyWithValue("name", "contact_name_2")
                .hasFieldOrPropertyWithValue("address", "contact_addr_2")
                .hasFieldOrPropertyWithValue("telephone", "contact_phone_2");

        Assertions.assertThat(order.getOrderGoodsList())
                .isNotNull()
                .isNotEmpty()
                .extracting(goods -> goods.getSkuCode().getCode(), Goods::getQty, Goods::getPrice)
                .contains(Assertions.tuple(skuCode1.getCode(), 100, new BigDecimal("100.00")),
                        Assertions.tuple(skuCode2.getCode(), 200, new BigDecimal("200.00")));

        Mockito.verify(applicationEvents, Mockito.times(1)).transferInPhysicalStore(orderId);
    }

    @Test
    public void transferOutLogicStore() throws Exception {
    }

    @Test
    public void transferLogicStore() throws Exception {
    }

    @Test
    public void findSalesStore() throws Exception {
    }

}