package com.chellus.TiendaCRUD.repositories;

import com.chellus.TiendaCRUD.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
