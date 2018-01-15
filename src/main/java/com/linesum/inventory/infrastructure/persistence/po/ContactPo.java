package com.linesum.inventory.infrastructure.persistence.po;

import javax.persistence.*;

/**
 * Created by zhengjx on 2017/10/25.
 */
@Entity
@Table(name = "contact")
public class ContactPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String telephone;

    public ContactPo(Long id, String name, String address, String telephone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
