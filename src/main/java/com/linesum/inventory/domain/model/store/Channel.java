package com.linesum.inventory.domain.model.store;

import com.linesum.inventory.domain.shared.Entity;
import com.linesum.inventory.domain.shared.ValueObject;

import java.util.Objects;

/**
 * 渠道
 */
public class Channel implements Entity<Channel> {

    private ChannelId channelId;

    private String name;

    private ChannelType channelType;

    private ChannelId parentChannelId;

    public Channel(ChannelId channelId, String name, ChannelType channelType, ChannelId parentChannelId) {
        this.channelId = channelId;
        this.name = name;
        this.channelType = channelType;
        this.parentChannelId = parentChannelId;
    }

    public ChannelId getChannelId() {
        return channelId;
    }

    public String getName() {
        return name;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public ChannelId getParentChannelId() {
        return parentChannelId;
    }

    @Override
    public boolean sameIdentityAs(Channel other) {
        return other != null && this.channelId.sameValueAs(other.channelId);
    }

    public static class ChannelId implements ValueObject<ChannelId> {

        private Long id;

        public ChannelId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        @Override
        public boolean sameValueAs(ChannelId other) {
            return other != null && Objects.equals(this.id, other.id);
        }
    }

    public enum ChannelType implements ValueObject<ChannelType> {
        SUPPLIER, AGENT, SHOP;

        ChannelType() {
        }

        @Override
        public boolean sameValueAs(ChannelType other) {
            return other != null && Objects.equals(this, other);
        }
    }

}
