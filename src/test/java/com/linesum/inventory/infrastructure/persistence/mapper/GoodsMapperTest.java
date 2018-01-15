package com.linesum.inventory.infrastructure.persistence.mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linesum.inventory.BaseJunitTestCase;
import com.linesum.inventory.infrastructure.persistence.po.GoodsPo;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * mybatis 通用mapper测试
 * 项目地址：https://github.com/abel533/Mapper
 * 文档地址：https://gitee.com/free/Mapper/blob/master/wiki/mapper3/5.Mappers.md
 */
public class GoodsMapperTest extends BaseJunitTestCase {

    @Autowired
    private GoodsMapper goodsMapper;

    private final String skuCode1 = UUID.randomUUID().toString();
    private final GoodsPo goodsPo1 = new GoodsPo(null, skuCode1, new BigDecimal("100.00"));

    private final String skuCode2 = UUID.randomUUID().toString();
    private final GoodsPo goodsPo2 = new GoodsPo(null, skuCode2, new BigDecimal("100.00"));

    private final String skuCode3 = UUID.randomUUID().toString();
    private final GoodsPo goodsPo3 = new GoodsPo(null, skuCode3, new BigDecimal("100.00"));

    @Test
    public void testInsert() throws Exception {
        // 动词 + Selective 为增量操作，否则为全量

        GoodsPo goodsPo;

        int insertRes = goodsMapper.insert(goodsPo1); // 全量
        assertThat(insertRes).isEqualTo(1);
        assertThat(goodsPo1.getId()).isNotNull();
        goodsPo = goodsMapper.selectByPrimaryKey(goodsPo1.getId());
        this.assertGoodsPo(goodsPo, goodsPo1);

        GoodsPo goodsPoTemp = new GoodsPo(null, UUID.randomUUID().toString(), null);
        goodsMapper.insertSelective(goodsPoTemp); // 增量
        assertThat(goodsPoTemp.getId()).isNotNull();
        goodsPo = goodsMapper.selectByPrimaryKey(goodsPoTemp.getId());
        this.assertGoodsPo(goodsPo, goodsPoTemp);
    }

    @Test
    public void testInsertList() throws Exception {
        // 批量插入，mysql独有
        int insertListRes = goodsMapper.insertList(Lists.newArrayList(goodsPo1, goodsPo2, goodsPo3));
        assertThat(insertListRes).isEqualTo(3);
    }

    @Test
    public void testSelect() throws Exception {
        this.testInsertList();

        GoodsPo goodsPo;
        List<GoodsPo> goodsPoList;

        // 主键查询
        goodsPo = goodsMapper.selectByPrimaryKey(goodsPo1.getId());
        this.assertGoodsPo(goodsPo, goodsPo1);

        // 精确条件单查询
        goodsPo = goodsMapper.selectOne(new GoodsPo(null, skuCode1, null));
        this.assertGoodsPo(goodsPo, goodsPo1);

        // 精确条件多查询
        goodsPoList = goodsMapper.select(new GoodsPo(null, skuCode1, null));
        assertThat(goodsPoList).isNotNull().isNotEmpty();
        this.assertGoodsPo(goodsPoList.get(0), goodsPo1);

        // 多主键查询
        goodsPoList = goodsMapper.selectByIds(
                StringUtils.join(Lists.newArrayList(goodsPo1.getId(), goodsPo2.getId(), goodsPo3.getId()), ","));
        assertThat(goodsPoList).isNotNull().isNotEmpty().hasSize(3);

        // 计数
        int goodsCount = goodsMapper.selectCount(new GoodsPo(null, null, new BigDecimal("100.00")));
        assertThat(goodsCount).isEqualTo(3);

        // Example查询(条件为数据库列名而非java属性名)
        Example goodsExample = new Example(GoodsPo.class);
        goodsExample.createCriteria().andCondition("1=1 and price like '%00%'");
        goodsExample.setOrderByClause("id desc");
        goodsPoList = goodsMapper.selectByExample(goodsExample);
        assertThat(goodsPoList).isNotNull().isNotEmpty().hasSize(3);
    }

    private void assertGoodsPo(GoodsPo target, GoodsPo source) {
        assertThat(target)
                .extracting(GoodsPo::getId, GoodsPo::getSkuCode, GoodsPo::getPrice)
                .contains(source.getId(), source.getSkuCode(), source.getPrice());
    }


    @Test
    public void testPage() throws Exception {
        this.testInsertList();

        PageHelper.startPage(0, 2);
        Page<GoodsPo> goodsPoPage = (Page<GoodsPo>) goodsMapper.selectAll(); // 可以直接使用Page<T>
        PageInfo<GoodsPo> goodsPoPageInfo = goodsPoPage.toPageInfo(); // 也可以使用PageInfo<T>

        assertThat(goodsPoPage).isNotNull();
        assertThat(goodsPoPageInfo.getTotal()).isEqualTo(3); // 总数
        assertThat(goodsPoPageInfo.getPages()).isEqualTo(2); // 总页数
        assertThat(goodsPoPageInfo.getList()).isNotEmpty().hasSize(goodsPoPageInfo.getSize()); // 每页2条记录
    }
}