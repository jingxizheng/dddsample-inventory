package com.linesum.inventory.infrastructure.persistence.jpa.po;

import com.linesum.inventory.domain.model.store.Channel;

import javax.persistence.*;

/**
 * Created by zhengjx on 2017/10/30.
 */
@Entity
@Table(name = "channel")
public class ChannelPo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Channel.ChannelType channelType;

    private Long parentChannelId;

    public ChannelPo(Long id, String name, Channel.ChannelType channelType, Long parentChannelId) {
        this.id = id;
        this.name = name;
        this.channelType = channelType;
        this.parentChannelId = parentChannelId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Channel.ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(Channel.ChannelType channelType) {
        this.channelType = channelType;
    }

    public Long getParentChannelId() {
        return parentChannelId;
    }

    public void setParentChannelId(Long parentChannelId) {
        this.parentChannelId = parentChannelId;
    }
}
