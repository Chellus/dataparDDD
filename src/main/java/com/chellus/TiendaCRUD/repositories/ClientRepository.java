package com.chellus.TiendaCRUD.repositories;

import com.chellus.TiendaCRUD.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
