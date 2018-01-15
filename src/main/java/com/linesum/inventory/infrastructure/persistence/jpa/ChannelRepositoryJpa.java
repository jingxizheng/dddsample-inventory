package com.linesum.inventory.infrastructure.persistence.jpa;

import com.linesum.inventory.infrastructure.persistence.po.ChannelPo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhengjx on 2017/10/31.
 */
@Repository
public interface ChannelRepositoryJpa extends CrudRepository<ChannelPo, Long> {
}
