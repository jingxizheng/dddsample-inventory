package com.linesum.inventory.domain.model.store;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.model.storeconfig.StoreConfig;
import com.linesum.inventory.domain.model.storeconfig.StoreConfigHandler;
import com.linesum.inventory.domain.model.storeconfig.StoreConfigHandlerImpl;
import com.linesum.inventory.domain.shared.Entity;
import com.linesum.inventory.domain.shared.ValueObject;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 渠道销售库存
 */
public class SalesStore implements Entity<SalesStore> {

    private SalesStoreId salesStoreId;

    private StoreType storeType = StoreType.TYPE_SALES;

    private List<Goods> goodsList;

    private PhysicalStore physicalStore; // 所属物理库存

    private Channel channel; // 销售库存所属渠道

    private List<StoreConfigHandler> storeConfigHandlers; // 附加的库存规则

    private List<SalesStore> otherSalesStoreList; // 同一物理库存下其他的渠道销售库存

    private List<Goods> lockGoodsList; // 锁库库存

    public SalesStore(PhysicalStore physicalStore,
                      Channel channel,
                      List<StoreConfigHandler> storeConfigHandlers,
                      List<SalesStore> otherSalesStore) {
        Preconditions.checkNotNull(physicalStore, "physicalStore is required");
        Preconditions.checkNotNull(channel, "channel is required");
        Preconditions.checkNotNull(storeConfigHandlers, "storeConfigHandlers is required");
        Preconditions.checkNotNull(otherSalesStore, "otherSalesStoreList is required");
        this.physicalStore = physicalStore;
        this.channel = channel;
        this.storeConfigHandlers = storeConfigHandlers;
        this.otherSalesStoreList = otherSalesStore;
        this.lockGoodsList = this.calumniateLockGoodsList(physicalStore.getGoodsList());
        this.goodsList = this.executeStoreConfig(physicalStore.getGoodsList());
    }

    /**
     * 计算可用库存，即剔除被锁库存
     */
    private List<Goods> calculateUsableGoodsList(List<Goods> goodsList) {
        goodsList.forEach(goods -> {
            otherSalesStoreList.forEach(otherSalesStore -> {
                otherSalesStore.getLockGoodsList().forEach(lockGoods -> {
                    if (goods.sameIdentityAs(lockGoods)) {
                        goods.reduce(lockGoods.getQty());
                    }
                });
            });
        });
        return goodsList;
    }

    /**
     * 执行库存规则
     */
    private List<Goods> executeStoreConfig(List<Goods> goodsList) {
        Iterator<StoreConfigHandler> configHandlerIterator = storeConfigHandlers.iterator();
        StoreConfig storeConfig = new StoreConfig(this.calculateUsableGoodsList(goodsList));
        while (configHandlerIterator.hasNext()) {
            configHandlerIterator.next().handleConfig(storeConfig);
        }
        return storeConfig.getGoodsList();
    }

    /**
     * 计算锁库库存
     */
    private List<Goods> calumniateLockGoodsList(List<Goods> goodsList) {
        Iterator<StoreConfigHandler> configHandlerIterator = storeConfigHandlers.iterator();
        StoreConfig storeConfig = new StoreConfig(goodsList);
        while (configHandlerIterator.hasNext()) {
            StoreConfigHandler storeConfigHandler = configHandlerIterator.next();
            if (storeConfigHandler instanceof StoreConfigHandlerImpl.LockRatioConfigHandler) {
                storeConfigHandler.handleConfig(storeConfig);
                break;
            }
        }
        return storeConfig.getGoodsList();
    }

    @Override
    public boolean sameIdentityAs(SalesStore other) {
        return other != null && this.salesStoreId.sameValueAs(other.getSalesStoreId());
    }

    public SalesStoreId getSalesStoreId() {
        return salesStoreId;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public PhysicalStore getPhysicalStore() {
        return physicalStore;
    }

    public Channel getChannel() {
        return channel;
    }

    public List<StoreConfigHandler> getStoreConfigHandlers() {
        return storeConfigHandlers;
    }

    public List<SalesStore> getOtherSalesStoreList() {
        return otherSalesStoreList;
    }

    public List<Goods> getLockGoodsList() {
        return lockGoodsList;
    }


    public static class SalesStoreId implements ValueObject<SalesStoreId> {

        private Long id;

        public SalesStoreId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        @Override
        public boolean sameValueAs(SalesStoreId other) {
            return other != null && Objects.equals(this.id, other.getId());
        }
    }
}
