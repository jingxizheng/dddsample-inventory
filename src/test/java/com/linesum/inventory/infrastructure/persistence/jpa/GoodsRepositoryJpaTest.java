package com.linesum.inventory.infrastructure.persistence.jpa;

import com.linesum.inventory.infrastructure.persistence.BaseJunitTestCase;
import com.linesum.inventory.infrastructure.persistence.jpa.po.GoodsPo;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by zhengjx on 2017/10/26.
 */
public class GoodsRepositoryJpaTest extends BaseJunitTestCase {

    @Autowired
    private GoodsRepositoryJpa goodsRepositoryJpa;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws Exception {
        GoodsPo goodsPo = goodsRepositoryJpa.save(new GoodsPo(null, UUID.randomUUID().toString(), new BigDecimal("100.00")));
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

    /**
     * CrudRepository.save()
     * 主键生成策略采用<code>strategy = GenerationType.AUTO</code>模式
     * 对于add情况 主键为数据库自增 手动设置主键无效
     */
    @Test
    public void testSaveWithId() throws Exception {
        GoodsPo goodsPo = goodsRepositoryJpa.save(new GoodsPo(null, UUID.randomUUID().toString(), new BigDecimal("100.00")));
        Long id = goodsPo.getId();
        goodsRepositoryJpa.save(new GoodsPo(id, UUID.randomUUID().toString(), new BigDecimal("200.00")));
        GoodsPo goodsPo2 = goodsRepositoryJpa.findOne(id);
        Assertions.assertThat(goodsPo2)
                .hasFieldOrPropertyWithValue("price", new BigDecimal("200.00"));
    }

}