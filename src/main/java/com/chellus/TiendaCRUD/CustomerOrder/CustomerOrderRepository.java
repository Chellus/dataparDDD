package com.chellus.TiendaCRUD.CustomerOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    @Query("SELECT o FROM CustomerOrder o WHERE o.deletedAt IS NULL")
    List<CustomerOrder> findAllActive();

    @Query("SELECT o FROM CustomerOrder o WHERE o.id = :id AND o.deletedAt IS NULL")
    Optional<CustomerOrder> findByIdActive(Long id);
}
