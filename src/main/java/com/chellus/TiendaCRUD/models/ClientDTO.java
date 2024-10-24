package com.chellus.TiendaCRUD.models;

import java.util.List;

public class ClientDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private List<Long> customerOrdersId;

    public ClientDTO(Long id, String name, String address, List<Long> customerOrdersId, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.customerOrdersId = customerOrdersId;
        this.phone = phone;
    }

    public ClientDTO() {

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Long> getCustomerOrdersId() {
        return customerOrdersId;
    }

    public void setCustomerOrdersId(List<Long> customerOrdersId) {
        this.customerOrdersId = customerOrdersId;
    }
}
