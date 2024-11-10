package com.chellus.TiendaCRUD.CustomerOrder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class CustomerOrderDTO {
    private Long id;

    @NotNull
    private Long clientId;

    @NotEmpty(message = "Order can't be empty")
    private Set<ProductSummaryDTO> orderProducts = new HashSet<>();

    private double totalPrice;
    private int quantity;

    private LocalDateTime orderDate;

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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
