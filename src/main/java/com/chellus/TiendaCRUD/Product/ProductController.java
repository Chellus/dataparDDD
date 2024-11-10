package com.chellus.TiendaCRUD.Product;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Get all available products
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO product) {
        ProductDTO productCreated = productService.createProduct(product);
        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
    }

    // Update existing product
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,@RequestBody @Valid ProductDTO product) {
        ProductDTO productUpdated = productService.updateProduct(id, product);
        return new ResponseEntity<>(productUpdated, HttpStatus.OK);
    }

    // Delete product by id
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {
        ProductDTO deletedProduct = productService.softDeleteProduct(id);
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }

}
