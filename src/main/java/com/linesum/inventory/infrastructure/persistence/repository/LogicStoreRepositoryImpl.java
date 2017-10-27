package com.linesum.inventory.infrastructure.persistence.repository;

import com.linesum.inventory.domain.model.store.*;
import com.linesum.inventory.infrastructure.persistence.jpa.GoodsRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.jpa.LogicStoreGoodsMiddleRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.jpa.LogicStoreRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.jpa.po.GoodsPo;
import com.linesum.inventory.infrastructure.persistence.jpa.po.LogicStoreGoodsMiddlePo;
import com.linesum.inventory.infrastructure.persistence.jpa.po.LogicStorePo;
import com.linesum.inventory.infrastructure.persistence.jpa.po.PhysicalStoreGoodsMiddlePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by zhengjx on 2017/10/27.
 */
@Repository
public class LogicStoreRepositoryImpl implements LogicStoreRepository {

    @Autowired
    private LogicStoreRepositoryJpa logicStoreRepositoryJpa;

    @Autowired
    private LogicStoreGoodsMiddleRepositoryJpa logicStoreGoodsMiddleRepositoryJpa;

    @Autowired
    private GoodsRepositoryJpa goodsRepositoryJpa;

    @Override
    public LogicStore find(LogicStore.LogicStoreId logicStoreId) {
        LogicStorePo logicStorePo = logicStoreRepositoryJpa.findOne(logicStoreId.getId());

        List<LogicStoreGoodsMiddlePo> logicStoreGoodsMiddlePoList = logicStoreGoodsMiddleRepositoryJpa.findByLogicStoreId(logicStoreId.getId());
        List<Long> goodsIdList = logicStoreGoodsMiddlePoList.stream()
                .map(LogicStoreGoodsMiddlePo::getGoodsId)
                .collect(Collectors.toList());
        List<GoodsPo> goodsPoList = goodsRepositoryJpa.findByIdIn(goodsIdList);

        List<Goods> goodsList = logicStoreGoodsMiddlePoList.stream()
                .map(lsgmPo -> {
                    GoodsPo goodsPo = goodsPoList.stream()
                            .filter(gPo -> Objects.equals(lsgmPo.getGoodsId(), gPo.getId()))
                            .findFirst()
                            .get(); // FIXME
                    return new Goods(new SkuCode(goodsPo.getSkuCode()), lsgmPo.getQty(), goodsPo.getPrice());
                })
                .collect(Collectors.toList());

        return new LogicStore(logicStoreId,
                goodsList,
                new PhysicalStore(
                        new PhysicalStore.PhysicalStoreId(logicStorePo.getPhysicalStoreId()),
                        null, null, null
                ));
    }

    @Override
    public LogicStore.LogicStoreId save(LogicStore logicStore) {
        Long logicStoreId = logicStore.getLogicStoreId().getId();
        Long physicalStoreId = logicStore.getPhysicalStore().getPhysicalStoreId().getId();
        LogicStorePo logicStorePo = logicStoreRepositoryJpa.save(new LogicStorePo(logicStoreId, physicalStoreId));

        logicStoreGoodsMiddleRepositoryJpa.deleteByLogicStoreId(logicStoreId);

        List<Goods> goodsList = logicStore.getGoodsList();
        List<LogicStoreGoodsMiddlePo> logicStoreGoodsMiddlePoList = goodsList.stream()
                .map(goods -> new LogicStoreGoodsMiddlePo(null,
                        logicStorePo.getId(),
                        goodsRepositoryJpa.findFirstBySkuCode(goods.getSkuCode().getCode()).getId(),
                        goods.getQty()))
                .collect(Collectors.toList());

        logicStoreGoodsMiddleRepositoryJpa.save(logicStoreGoodsMiddlePoList);
        return new LogicStore.LogicStoreId(logicStorePo.getId());
    }
}
