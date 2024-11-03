package com.chellus.TiendaCRUD.CustomerOrder;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class CustomerOrderController {

    @Autowired
    CustomerOrderService customerOrderService;

    // get every order
    @GetMapping
    public ResponseEntity<List<CustomerOrderDTO>> findAll() {
        List<CustomerOrderDTO> customerOrders = customerOrderService.getAllCustomerOrders();
        return new ResponseEntity<>(customerOrders, HttpStatus.OK);
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrderDTO> findById(@PathVariable Long id) {
        CustomerOrderDTO customerOrderDTO = customerOrderService.getCustomerOrderById(id);
        return new ResponseEntity<>(customerOrderDTO, HttpStatus.OK);
    }

    // create new order, we need to update every product
    @PostMapping
    public ResponseEntity<CustomerOrderDTO> create(@RequestBody @Valid CustomerOrderDTO customerOrder) {

        CustomerOrderDTO createdOrder = customerOrderService.createCustomerOrder(customerOrder);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);

    }

    // update existing order by ID
    @PutMapping("/{id}")
    public ResponseEntity<CustomerOrderDTO> update(@PathVariable Long id, @RequestBody @Valid CustomerOrderDTO customerOrder) {
        CustomerOrderDTO updatedOrder = customerOrderService.updateCustomerOrder(id, customerOrder);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    // delete existing order
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerOrderDTO> delete(@PathVariable Long id) {
        CustomerOrderDTO deletedOrder = customerOrderService.deleteCustomerOrder(id);
        return new ResponseEntity<>(deletedOrder, HttpStatus.OK);
    }
}
