package com.chellus.TiendaCRUD.controllers;

import com.chellus.TiendaCRUD.models.CustomerOrder;
import com.chellus.TiendaCRUD.repositories.CustomerOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class CustomerOrderController {
    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    //
    @GetMapping
    public ResponseEntity<List<CustomerOrder>> findAll() {
        List<CustomerOrder> customerOrders = customerOrderRepository.findAll();
        return new ResponseEntity<>(customerOrders, HttpStatus.OK);
    }


}
