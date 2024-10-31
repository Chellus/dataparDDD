package com.chellus.TiendaCRUD.Product;

public class OrderSummaryDTO {
    Long orderId;
    int quantity;

    public OrderSummaryDTO() {}

    public OrderSummaryDTO(Long orderId, int quantity) {
        this.orderId = orderId;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
