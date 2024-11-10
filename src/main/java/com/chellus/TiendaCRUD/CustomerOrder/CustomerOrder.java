package com.chellus.TiendaCRUD.CustomerOrder;

import com.chellus.TiendaCRUD.Client.Client;
import com.chellus.TiendaCRUD.OrderProduct.OrderProduct;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProduct> orderItems;

    private double totalPrice;
    private int quantity;
    private LocalDateTime orderDate;

    private LocalDateTime deletedAt;

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "id=" + id +
                ", client=" + client +
                ", orderItems=" + orderItems +
                ", totalPrice=" + totalPrice +
                ", quantity=" + quantity +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<OrderProduct> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderProduct> orderItems) {
        this.orderItems = orderItems;
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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
