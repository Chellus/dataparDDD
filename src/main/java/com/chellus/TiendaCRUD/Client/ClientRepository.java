package com.chellus.TiendaCRUD.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE c.deletedAt IS NULL")
    List<Client> findAllActive();

    @Query("SELECT c FROM Client c WHERE c.id = :id AND c.deletedAt IS NULL")
    Optional<Client> findByIdActive(Long id);
}
