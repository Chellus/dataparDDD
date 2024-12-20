package com.chellus.TiendaCRUD.CustomerOrder;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductSummaryDTO {
    @NotNull
    Long productId;
    @Min(1)
    int quantity;

    public ProductSummaryDTO() {}

    public ProductSummaryDTO(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
