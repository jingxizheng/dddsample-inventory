package com.linesum.inventory.infrastructure.persistence.jpa;

import com.linesum.inventory.Application;
import com.linesum.inventory.infrastructure.persistence.jpa.po.GoodsPo;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhengjx on 2017/10/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GoodsRepositoryJpaTest {

    @Autowired
    private GoodsRepositoryJpa goodsRepositoryJpa;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws Exception {
        GoodsPo goodsPo = goodsRepositoryJpa.save(new GoodsPo(null, "test_sku_code_1", new BigDecimal("100.00")));
        Assertions.assertThat(goodsPo)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept();

        GoodsPo goodsPo1 = goodsRepositoryJpa.findOne(goodsPo.getId());
        Assertions.assertThat(goodsPo1)
                .isNotNull();

        GoodsPo goodsPo2 = goodsRepositoryJpa.findFirstBySkuCode(goodsPo.getSkuCode());
        Assertions.assertThat(goodsPo2)
                .isNotNull();

        List<GoodsPo> all = (List<GoodsPo>) goodsRepositoryJpa.findAll();

        List<GoodsPo> goodsPoList = goodsRepositoryJpa.findByIdIn(all.stream()
                .map(GoodsPo::getId).collect(Collectors.toList()));
        Assertions.assertThat(goodsPoList)
                .isNotNull()
                .isNotEmpty()
                .first()
                .hasNoNullFieldsOrPropertiesExcept();

        List<GoodsPo> goodsPoList1 = goodsRepositoryJpa.findBySkuCodeIn(all.stream()
                .map(GoodsPo::getSkuCode).collect(Collectors.toList()));
        Assertions.assertThat(goodsPoList1)
                .isNotNull()
                .isNotEmpty()
                .first()
                .hasNoNullFieldsOrPropertiesExcept();
    }

}