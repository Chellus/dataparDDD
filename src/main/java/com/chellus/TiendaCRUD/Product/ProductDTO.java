package com.chellus.TiendaCRUD.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

public class ProductDTO {
    private Long id;
    @NotBlank(message = "Name can't be blank")
    private String productName;
    @NotBlank(message = "Description can't be blank")
    private String productDescription;
    @Min(value = 0, message = "Price can't negative")
    private double productPrice;
    @Min(value = 0, message = "Stock can't be negative")
    private int productStock;
    private Set<OrderSummaryDTO> orders = new HashSet<>();


    public ProductDTO() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public Set<OrderSummaryDTO> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderSummaryDTO> orderIds) {
        this.orders = orderIds;
    }
}
