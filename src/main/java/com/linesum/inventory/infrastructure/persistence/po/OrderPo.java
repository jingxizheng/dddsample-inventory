package com.linesum.inventory.infrastructure.persistence.po;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhengjx on 2017/11/2.
 */
@Entity
@Table(name = "order_info")
public class OrderPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;

    private Long acceptorId;

    private Date sendDate;

    private Date acceptDate;

    public OrderPo(Long id, Long senderId, Long acceptorId, Date sendDate, Date acceptDate) {
        this.id = id;
        this.senderId = senderId;
        this.acceptorId = acceptorId;
        this.sendDate = sendDate;
        this.acceptDate = acceptDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(Long acceptorId) {
        this.acceptorId = acceptorId;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }
}
