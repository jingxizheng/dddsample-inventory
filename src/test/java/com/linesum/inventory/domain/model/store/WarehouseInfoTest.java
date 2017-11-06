package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.model.order.Contact;
import com.linesum.inventory.domain.model.order.ContactId;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhengjx on 2017/11/6.
 */
public class WarehouseInfoTest {

    private WarehouseInfo warehouseInfo;

    @Before
    public void setUp() throws Exception {
        warehouseInfo = new WarehouseInfo(
                new Contact(new ContactId(1L), "contact_name", "contact_address", "contact_phone"),
                100,
                200
        );
    }

    @Test
    public void addUsedCapacity() throws Exception {
        warehouseInfo.addUsedCapacity(50);
        Assertions.assertThat(warehouseInfo.getUsedCapacity()).isEqualTo(150);
    }

    @Test
    public void reduceUsedCapacity() throws Exception {
        warehouseInfo.reduceUsedCapacity(50);
        Assertions.assertThat(warehouseInfo.getUsedCapacity()).isEqualTo(50);
    }

    @Test
    public void enoughTotalCapacity() throws Exception {
        Assertions.assertThat(warehouseInfo.enoughTotalCapacity(100))
                .isTrue();

        Assertions.assertThat(warehouseInfo.enoughTotalCapacity(101))
                .isFalse();

        Assertions.assertThat(warehouseInfo.enoughTotalCapacity(null))
                .isTrue();
    }

    @Test
    public void enoughUsedCapacity() throws Exception {
        Assertions.assertThat(warehouseInfo.enoughUsedCapacity(100))
                .isTrue();

        Assertions.assertThat(warehouseInfo.enoughUsedCapacity(101))
                .isFalse();

        Assertions.assertThat(warehouseInfo.enoughUsedCapacity(null))
                .isTrue();
    }

}