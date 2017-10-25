package com.linesum.inventory.infrastructure.persistence.repository;

import com.linesum.inventory.domain.model.store.Goods;
import com.linesum.inventory.domain.model.store.GoodsRepository;
import com.linesum.inventory.domain.model.store.SkuCode;
import com.linesum.inventory.infrastructure.persistence.jpa.GoodsRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.jpa.po.GoodsPo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhengjx on 2017/10/25.
 */
public class GoodsRepositoryImpl implements GoodsRepository {

    @Autowired
    private GoodsRepositoryJpa goodsRepositoryJpa;

    @Override
    public List<Goods> find(List<SkuCode> skuCodeList) {
        List<String> skuCodeStringList = skuCodeList.stream()
                .map(SkuCode::codeString)
                .collect(Collectors.toList());
        List<GoodsPo> goodsPoList = goodsRepositoryJpa.findBySkuCodeIn(skuCodeStringList);
        return goodsPoList.stream()
                .map(goodsPo -> new Goods(new SkuCode(goodsPo.getSkuCode()), goodsPo.getQty(), goodsPo.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public Goods find(SkuCode skuCode) {
        GoodsPo goodsPo = goodsRepositoryJpa.findFirstBySkuCode(skuCode.codeString());
        return new Goods(new SkuCode(goodsPo.getSkuCode()),
                goodsPo.getQty(),
                goodsPo.getPrice());
    }
}
