package com.chellus.TiendaCRUD.CustomerOrder;

import com.chellus.TiendaCRUD.Exceptions.BadOrderException;
import com.chellus.TiendaCRUD.Exceptions.ClientNotFoundException;
import com.chellus.TiendaCRUD.Exceptions.OrderNotFoundException;
import com.chellus.TiendaCRUD.Exceptions.ProductNotFoundException;
import com.chellus.TiendaCRUD.OrderProduct.OrderProduct;
import com.chellus.TiendaCRUD.Product.Product;
import com.chellus.TiendaCRUD.Product.ProductRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
        try {
            CustomerOrderDTO customerOrderDTO = customerOrderService.getCustomerOrderById(id);
            return new ResponseEntity<>(customerOrderDTO, HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // create new order, we need to update every product
    @PostMapping
    public ResponseEntity<CustomerOrderDTO> create(@RequestBody CustomerOrderDTO customerOrder) {
        try {
            CustomerOrderDTO createdOrder = customerOrderService.createCustomerOrder(customerOrder);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (BadOrderException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ClientNotFoundException | ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // update existing order by ID
    @PutMapping("/{id}")
    public ResponseEntity<CustomerOrderDTO> update(@PathVariable Long id, @RequestBody CustomerOrderDTO customerOrder) {
        try {
            CustomerOrderDTO updatedOrder = customerOrderService.updateCustomerOrder(id, customerOrder);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (BadOrderException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ClientNotFoundException | ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete existing order
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerOrderDTO> delete(@PathVariable Long id) {
        try {
            CustomerOrderDTO deletedOrder = customerOrderService.deleteCustomerOrder(id);
            return new ResponseEntity<>(deletedOrder, HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
