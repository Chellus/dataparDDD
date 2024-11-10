package com.chellus.TiendaCRUD.Product;

import com.chellus.TiendaCRUD.Exceptions.ProductNotFoundException;
import com.chellus.TiendaCRUD.OrderProduct.OrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAllActive();

        return  products.stream()
                .map(this::toProductDTO)
                .toList();
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findByIdActive(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return toProductDTO(product);
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();

        product.setProductName(productDTO.getProductName());
        product.setProductDescription(productDTO.getProductDescription());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductStock(productDTO.getProductStock());

        Product savedProduct = productRepository.save(product);

        return toProductDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findByIdActive(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setProductName(productDTO.getProductName());
        product.setProductDescription(productDTO.getProductDescription());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductStock(productDTO.getProductStock());
        productRepository.save(product);

        Product updatedProduct = productRepository.save(product);

        return toProductDTO(updatedProduct);
    }

    public ProductDTO softDeleteProduct(Long id) {
        Product product = productRepository.findByIdActive(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        ProductDTO deletedProduct = toProductDTO(product);
        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);

        return deletedProduct;
    }

    private OrderSummaryDTO toOrderSummaryDTO(OrderProduct orderProduct) {
        OrderSummaryDTO orderSummaryDTO = new OrderSummaryDTO();

        orderSummaryDTO.setOrderId(orderProduct.getOrder().getId());
        orderSummaryDTO.setQuantity(orderProduct.getQuantity());

        return orderSummaryDTO;
    }

    private ProductDTO toProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setProductName(product.getProductName());
        productDTO.setProductDescription(product.getProductDescription());
        productDTO.setProductPrice(product.getProductPrice());
        productDTO.setProductStock(product.getProductStock());

        Set<OrderSummaryDTO> orderSummaryDTOs = new HashSet<>();

        if (product.getOrderItems() != null) {
            for (OrderProduct orderProduct : product.getOrderItems()) {
                orderSummaryDTOs.add(toOrderSummaryDTO(orderProduct));
            }
        }

        productDTO.setOrders(orderSummaryDTOs);

        return productDTO;
    }

}
