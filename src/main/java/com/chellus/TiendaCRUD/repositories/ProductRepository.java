package com.chellus.TiendaCRUD.repositories;

import com.chellus.TiendaCRUD.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
