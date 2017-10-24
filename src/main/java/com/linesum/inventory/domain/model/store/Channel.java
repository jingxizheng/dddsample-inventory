package com.linesum.inventory.domain.model.store;

import com.google.common.base.Preconditions;
import com.linesum.inventory.domain.shared.Entity;
import com.linesum.inventory.domain.shared.ValueObject;

import java.util.Objects;

/**
 * 渠道
 */
public class Channel implements Entity<Channel> {

    private ChannelId channelId;

    private ChannelType channelType;

    private Channel parentChannel;

    public Channel(ChannelId channelId, ChannelType channelType, Channel parentChannel) {
        Preconditions.checkNotNull(channelId, "channelId is required");
        Preconditions.checkNotNull(channelType, "channelType is required");
        Preconditions.checkNotNull(parentChannel, "parentChannel is required");
        this.channelId = channelId;
        this.channelType = channelType;
        this.parentChannel = parentChannel;
    }

    public boolean isShop() {
        return Objects.equals(this.channelType, ChannelType.SHOP);
    }

    @Override
    public boolean sameIdentityAs(Channel other) {
        return other != null && this.channelId.sameValueAs(other.channelId);
    }

    public class ChannelId implements ValueObject<ChannelId> {

        private Long id;

        @Override
        public boolean sameValueAs(ChannelId other) {
            return other != null && Objects.equals(this.id, other.id);
        }
    }

    public enum ChannelType implements ValueObject<ChannelType> {
        SUPPLIER, AGENT, SHOP;

        @Override
        public boolean sameValueAs(ChannelType other) {
            return other != null && Objects.equals(this, other);
        }
    }

}
