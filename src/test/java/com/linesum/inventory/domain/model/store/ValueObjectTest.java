package com.linesum.inventory.domain.model.store;

import org.junit.Test;

public class ValueObjectTest {

    @Test
    public void test() throws Exception {
        ValueObjectForTest obj1 = new ValueObjectForTest("prop", 1);
        ValueObjectForTest obj2 = new ValueObjectForTest("prop", 1);
        System.out.println(obj1 == obj2);
    }
}
