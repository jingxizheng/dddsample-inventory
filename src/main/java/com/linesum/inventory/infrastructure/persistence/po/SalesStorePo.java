package com.linesum.inventory.infrastructure.persistence.po;

import javax.persistence.*;

/**
 * Created by zhengjx on 2017/10/30.
 */
@Entity
@Table(name = "sales_store")
public class SalesStorePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long channelId;

    public SalesStorePo(Long id, Long channelId) {
        this.id = id;
        this.channelId = channelId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
}
