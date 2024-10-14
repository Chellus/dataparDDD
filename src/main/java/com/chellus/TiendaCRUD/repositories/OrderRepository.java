package com.chellus.TiendaCRUD.repositories;

import com.chellus.TiendaCRUD.models.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
}
