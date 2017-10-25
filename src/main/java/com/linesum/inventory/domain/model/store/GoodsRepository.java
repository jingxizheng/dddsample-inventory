package com.linesum.inventory.domain.model.store;

import java.util.List;

/**
 * Created by zhengjx on 2017/10/25.
 */
public interface GoodsRepository {

    List<Goods> find(List<SkuCode> skuCodeList);

    Goods find(SkuCode skuCode);
}
