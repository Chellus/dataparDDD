package com.chellus.TiendaCRUD.controllers;

import com.chellus.TiendaCRUD.models.CustomerOrder;
import com.chellus.TiendaCRUD.models.OrderProduct;
import com.chellus.TiendaCRUD.models.Product;
import com.chellus.TiendaCRUD.repositories.CustomerOrderRepository;
import com.chellus.TiendaCRUD.repositories.ProductRepository;
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
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private ProductRepository productRepository;

    // get every order
    @GetMapping
    public ResponseEntity<List<CustomerOrder>> findAll() {
        List<CustomerOrder> customerOrders = customerOrderRepository.findAll();
        return new ResponseEntity<>(customerOrders, HttpStatus.OK);
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrder> findById(@PathVariable Long id) {
        Optional<CustomerOrder> customerOrder = customerOrderRepository.findById(id);
        return customerOrder.map(order -> new ResponseEntity<>(order, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // create new order, we need to update every product
    @PostMapping
    public ResponseEntity<CustomerOrder> create(@RequestBody CustomerOrder customerOrder) {
        System.out.println(customerOrder);

        for (OrderProduct orderProduct : customerOrder.getOrderItems()) {
            orderProduct.setOrder(customerOrder);
        }
        customerOrder.setTotalPrice(this.calculateTotalPrice(customerOrder));

        this.updateProductsStock(customerOrder);
        customerOrderRepository.save(customerOrder);
        return new ResponseEntity<>(customerOrder, HttpStatus.CREATED);
    }

    // update existing order by ID
    @PutMapping("/{id}")
    public ResponseEntity<CustomerOrder> update(@PathVariable Long id, @RequestBody CustomerOrder customerOrder) {
        Optional<CustomerOrder> customerOrder1 = customerOrderRepository.findById(id);
        if (customerOrder1.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CustomerOrder updatedCustomerOrder = customerOrder1.get();
        updatedCustomerOrder.setClient(updatedCustomerOrder.getClient());
        updatedCustomerOrder.setOrderItems(customerOrder.getOrderItems());
        updatedCustomerOrder.setQuantity(customerOrder.getQuantity());
        customerOrder.setTotalPrice(this.calculateTotalPrice(customerOrder));
        this.updateProductsStock(updatedCustomerOrder);
        customerOrderRepository.save(updatedCustomerOrder);
        return new ResponseEntity<>(customerOrderRepository.save(updatedCustomerOrder), HttpStatus.OK);
    }

    // delete existing order
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerOrder> delete(@PathVariable Long id) {
        Optional<CustomerOrder> customerOrder = customerOrderRepository.findById(id);
        if (customerOrder.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerOrder.get().getOrderItems().size();
        customerOrderRepository.deleteById(id);
        return new ResponseEntity<>(customerOrderRepository.save(customerOrder.get()), HttpStatus.OK);
    }

    // TODO: check if this is actually necessary, I'm inclining towards no.
    // this isn't actually necessary because we're expecting the front-end to calculate the price and send it
    // in the body of the request
    private double calculateTotalPrice(CustomerOrder customerOrder) {
        double totalPrice = 0;

        for (OrderProduct orderProduct : customerOrder.getOrderItems()) {
            Optional<Product> optionalProduct = productRepository.findById(orderProduct.getProduct().getId());

            if (optionalProduct.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }

            Product product = optionalProduct.get();
            totalPrice += product.getProductPrice() * orderProduct.getQuantity();
        }

        return totalPrice;
    }

    private void updateProductsStock(CustomerOrder customerOrder) {
        for (OrderProduct orderProduct : customerOrder.getOrderItems()) {

            Optional<Product> optionalProduct = productRepository.findById(orderProduct.getProduct().getId());

            if (optionalProduct.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }

            Product product = optionalProduct.get();
            if (product.getProductStock() < orderProduct.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Too many products");
            }
            product.setProductStock(product.getProductStock() - orderProduct.getQuantity());
            productRepository.save(product);
        }
    }

}
