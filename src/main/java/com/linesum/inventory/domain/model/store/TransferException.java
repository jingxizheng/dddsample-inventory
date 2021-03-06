package com.linesum.inventory.domain.model.store;

/**
 * Created by zhengjx on 2017/11/6.
 */
public class TransferException extends RuntimeException {

    private final WarehouseId from;

    private final WarehouseId to;

    public TransferException(WarehouseId from, WarehouseId to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String getMessage() {
        return "store transfer form warehouse[" + from.idStrign() + "] to warehouse[" + to.idStrign() + "] is not in some physical store";
    }
}
