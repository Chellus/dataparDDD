package com.chellus.TiendaCRUD.Client;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ClientDTO {
    private Long id;
    @NotBlank(message = "Name can not be blank")
    private String name;
    @NotBlank(message = "Address can not be blank")
    private String address;
    @NotBlank(message = "Phone can not be blank")
    private String phone;
    private List<Long> customerOrdersId;

    public ClientDTO() {}

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
