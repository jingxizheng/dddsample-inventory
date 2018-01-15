package com.linesum.inventory.infrastructure.persistence.repository;

import com.linesum.inventory.domain.model.store.*;
import com.linesum.inventory.domain.repository.LogicStoreRepository;
import com.linesum.inventory.infrastructure.persistence.jpa.GoodsRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.jpa.LogicStoreGoodsMiddleRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.jpa.LogicStoreRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.mapper.GoodsMapper;
import com.linesum.inventory.infrastructure.persistence.mapper.LogicStoreGoodsMiddleMapper;
import com.linesum.inventory.infrastructure.persistence.mapper.LogicStoreMapper;
import com.linesum.inventory.infrastructure.persistence.po.GoodsPo;
import com.linesum.inventory.infrastructure.persistence.po.LogicStoreGoodsMiddlePo;
import com.linesum.inventory.infrastructure.persistence.po.LogicStorePo;
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
    private LogicStoreMapper logicStoreMapper;

    @Autowired
    private LogicStoreGoodsMiddleRepositoryJpa logicStoreGoodsMiddleRepositoryJpa;

    @Autowired
    private LogicStoreGoodsMiddleMapper logicStoreGoodsMiddleMapper;

    @Autowired
    private GoodsRepositoryJpa goodsRepositoryJpa;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public LogicStore find(LogicStore.LogicStoreId logicStoreId) {
//        LogicStorePo logicStorePo = logicStoreMapper.selectByPrimaryKey(logicStoreId.getId());
        LogicStorePo logicStorePo = logicStoreRepositoryJpa.findOne(logicStoreId.getId());

//        List<LogicStoreGoodsMiddlePo> logicStoreGoodsMiddlePoList = logicStoreGoodsMiddleMapper.findByLogicStoreId(logicStoreId.getId());
        List<LogicStoreGoodsMiddlePo> logicStoreGoodsMiddlePoList = logicStoreGoodsMiddleRepositoryJpa.findByLogicStoreId(logicStoreId.getId());
        List<Long> goodsIdList = logicStoreGoodsMiddlePoList.stream()
                .map(LogicStoreGoodsMiddlePo::getGoodsId)
                .collect(Collectors.toList());
//        List<GoodsPo> goodsPoList = goodsMapper.findByIdIn(goodsIdList);
        List<GoodsPo> goodsPoList = goodsRepositoryJpa.findByIdIn(goodsIdList);

        List<Goods> goodsList = logicStoreGoodsMiddlePoList.stream()
                .map(lsgmPo -> {
                    GoodsPo goodsPo = goodsPoList.stream()
                            .filter(gPo -> Objects.equals(lsgmPo.getGoodsId(), gPo.getId()))
                            .findFirst()
                            .get();
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
//        LogicStorePo logicStorePo = new LogicStorePo(logicStoreId, physicalStoreId);
//        logicStoreMapper.insert(logicStorePo);
        LogicStorePo logicStorePo = logicStoreRepositoryJpa.save(new LogicStorePo(logicStoreId, physicalStoreId));

//        logicStoreGoodsMiddleMapper.deleteByLogicStoreId(logicStoreId);
        logicStoreGoodsMiddleRepositoryJpa.deleteByLogicStoreId(logicStoreId);

        List<Goods> goodsList = logicStore.getGoodsList();
        List<LogicStoreGoodsMiddlePo> logicStoreGoodsMiddlePoList = goodsList.stream()
                .map(goods -> new LogicStoreGoodsMiddlePo(null,
                        logicStorePo.getId(),
//                        goodsMapper.findFirstBySkuCode(goods.getSkuCode().getCode()).getId(),
                        goodsRepositoryJpa.findFirstBySkuCode(goods.getSkuCode().getCode()).getId(),
                        goods.getQty()))
                .collect(Collectors.toList());

//        logicStoreGoodsMiddleMapper.insertList(logicStoreGoodsMiddlePoList);
        logicStoreGoodsMiddleRepositoryJpa.save(logicStoreGoodsMiddlePoList);
        return new LogicStore.LogicStoreId(logicStorePo.getId());
    }
}
