package com.chellus.TiendaCRUD.CustomerOrder;

import com.chellus.TiendaCRUD.OrderProduct.OrderProductDTO;

import java.util.HashSet;
import java.util.Set;

public class CustomerOrderDTO {
    private Long id;
    private Long clientId;
    private Set<ProductSummaryDTO> orderProducts = new HashSet<>();
    private double totalPrice;
    private int quantity;

    public CustomerOrderDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Set<ProductSummaryDTO> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<ProductSummaryDTO> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
