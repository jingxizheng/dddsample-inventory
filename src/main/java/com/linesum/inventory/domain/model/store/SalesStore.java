package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.model.storeconfig.StoreConfig;
import com.linesum.inventory.domain.model.storeconfig.StoreConfigHandlerImpl;
import com.linesum.inventory.domain.shared.Entity;
import com.linesum.inventory.domain.shared.ValueObject;

import java.util.*;

/**
 * 渠道销售库存
 */
public class SalesStore implements Entity<SalesStore> {

    private SalesStoreId salesStoreId;

    private StoreType storeType = StoreType.TYPE_SALES;

    private List<Goods> goodsList = Collections.EMPTY_LIST;

    private List<LogicStore> logicStoreList; // 所属逻辑库存

    private Channel channel; // 销售库存所属渠道

    private List<StoreConfig> storeConfigList; // 附加的库存规则

    private static final StoreConfigHandlerImpl storeConfigHandler = new StoreConfigHandlerImpl();

    public SalesStore(SalesStoreId salesStoreId,
                      List<LogicStore> logicStoreList,
                      Channel channel,
                      List<StoreConfig> storeConfigList) {
        this.salesStoreId = salesStoreId;
        this.logicStoreList = logicStoreList;
        this.channel = channel;
        this.storeConfigList = storeConfigList;
        this.goodsList = this.executeStoreConfig();
    }

    /**
     * 执行库存规则
     */
    private List<Goods> executeStoreConfig() {
        List<Goods> goodsListSeed = Collections.EMPTY_LIST;
        this.logicStoreList.forEach(logicStore ->
                logicStore.getGoodsList().forEach(goods ->
                        this.addGoods(goodsListSeed, goods)));

        return storeConfigHandler.execute(this.storeConfigList, goodsListSeed);
    }

    private void addGoods(List<Goods> goodsList, Goods target) {
        Optional<Goods> goodsOptional = goodsList.stream()
                .filter(goods -> goods.sameIdentityAs(target))
                .findFirst();
        if (goodsOptional.isPresent()) {
            goodsOptional.get().add(target.getQty());
        } else {
            goodsList.add(target);
        }
    }

    @Override
    public boolean sameIdentityAs(SalesStore other) {
        return other != null && this.salesStoreId.sameValueAs(other.getSalesStoreId());
    }

    public SalesStoreId getSalesStoreId() {
        return salesStoreId;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public List<LogicStore> getLogicStoreList() {
        return logicStoreList;
    }

    public Channel getChannel() {
        return channel;
    }

    public List<StoreConfig> getStoreConfigList() {
        return storeConfigList;
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
