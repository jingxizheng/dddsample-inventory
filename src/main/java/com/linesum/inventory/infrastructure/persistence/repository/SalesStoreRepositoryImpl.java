package com.linesum.inventory.infrastructure.persistence.repository;

import com.linesum.inventory.domain.model.store.*;
import com.linesum.inventory.domain.model.storeconfig.StoreConfig;
import com.linesum.inventory.infrastructure.persistence.jpa.ChannelRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.jpa.SalesRatioConfigRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.jpa.SalesStoreLogicStoreMiddleRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.jpa.SalesStoreRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.jpa.po.ChannelPo;
import com.linesum.inventory.infrastructure.persistence.jpa.po.SalesRatioConfigPo;
import com.linesum.inventory.infrastructure.persistence.jpa.po.SalesStoreLogicStoreMiddlePo;
import com.linesum.inventory.infrastructure.persistence.jpa.po.SalesStorePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhengjx on 2017/10/31.
 */
@Repository
public class SalesStoreRepositoryImpl implements SalesStoreRepository {

    @Autowired
    private ChannelRepositoryJpa channelRepositoryJpa;

    @Autowired
    private SalesStoreLogicStoreMiddleRepositoryJpa salesStoreLogicStoreMiddleRepositoryJpa;

    @Autowired
    private SalesStoreRepositoryJpa salesStoreRepositoryJpa;

    @Autowired
    private SalesRatioConfigRepositoryJpa salesRatioConfigRepositoryJpa;

    @Autowired
    private LogicStoreRepository logicStoreRepository;

    @Override
    public SalesStore find(SalesStore.SalesStoreId salesStoreId) {
        SalesStorePo salesStorePo = salesStoreRepositoryJpa.findOne(salesStoreId.getId());
        ChannelPo channelPo = channelRepositoryJpa.findOne(salesStorePo.getChannelId());

        List<SalesStoreLogicStoreMiddlePo> sslsmPoList = salesStoreLogicStoreMiddleRepositoryJpa.findBySalesStoreId(salesStoreId.getId());

        List<LogicStore> logicStoreList = sslsmPoList.stream()
                .map(po -> logicStoreRepository.find(new LogicStore.LogicStoreId(po.getLogicStoreId())))
                .collect(Collectors.toList());


        List<StoreConfig> storeConfigList = Collections.EMPTY_LIST;
        SalesRatioConfigPo salesRatioConfigPo = salesRatioConfigRepositoryJpa.findFirstBySalesStoreId(salesStoreId.getId());
        if (salesRatioConfigPo != null) {
            storeConfigList.add(new StoreConfig.SalesRatioConfig(salesRatioConfigPo.getRatio()));
        }

        return new SalesStore(
                salesStoreId,
                logicStoreList,
                new Channel(
                        new Channel.ChannelId(channelPo.getId()),
                        channelPo.getName(),
                        channelPo.getChannelType(),
                        new Channel.ChannelId(channelPo.getParentChannelId())
                        ),
                storeConfigList);
    }

}
