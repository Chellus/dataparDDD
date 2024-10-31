package com.chellus.TiendaCRUD.CustomerOrder;

import com.chellus.TiendaCRUD.Client.Client;
import com.chellus.TiendaCRUD.Client.ClientRepository;
import com.chellus.TiendaCRUD.Exceptions.BadOrderException;
import com.chellus.TiendaCRUD.Exceptions.ClientNotFoundException;
import com.chellus.TiendaCRUD.Exceptions.OrderNotFoundException;
import com.chellus.TiendaCRUD.Exceptions.ProductNotFoundException;
import com.chellus.TiendaCRUD.OrderProduct.OrderProduct;
import com.chellus.TiendaCRUD.OrderProduct.OrderProductRepository;
import com.chellus.TiendaCRUD.Product.Product;
import com.chellus.TiendaCRUD.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomerOrderService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CustomerOrderDTO> getAllCustomerOrders() {
        List<CustomerOrder> customerOrders = customerOrderRepository.findAll();

        return customerOrders.stream().map(this::toCustomerOrderDTO).toList();
    }

    public CustomerOrderDTO getCustomerOrderById(Long id) {
        CustomerOrder customerOrder = customerOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        return toCustomerOrderDTO(customerOrder);
    }

    public CustomerOrderDTO createCustomerOrder(CustomerOrderDTO customerOrderDTO) {
        CustomerOrder customerOrder = new CustomerOrder();
        // find the client if it exits
        Client client = clientRepository.findById(customerOrderDTO.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(customerOrderDTO.getClientId()));

        System.out.println(client);

        customerOrder.setClient(client);
        customerOrder.setQuantity(customerOrderDTO.getOrderProducts()
                .stream().mapToInt(ProductSummaryDTO::getQuantity).sum());

        double totalPrice = 0.0f;
        Set<OrderProduct> orderProducts = new HashSet<>();

        // process each product in the order
        for (ProductSummaryDTO productSummaryDTO : customerOrderDTO.getOrderProducts()) {
            Product product = productRepository.findById(productSummaryDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(productSummaryDTO.getProductId()));

            // check if the product has enough stock
            if (product.getProductStock() < productSummaryDTO.getQuantity()) {
                throw new BadOrderException("Not enough stock");
            }

            // Update product stock
            product.setProductStock(product.getProductStock() - productSummaryDTO.getQuantity());
            productRepository.save(product);

            // create order product
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(customerOrder);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(productSummaryDTO.getQuantity());
            orderProducts.add(orderProduct);

            totalPrice += product.getProductPrice() * productSummaryDTO.getQuantity();
        }

        customerOrder.setOrderItems(orderProducts);
        customerOrder.setTotalPrice(totalPrice);

        customerOrderRepository.save(customerOrder);

        return toCustomerOrderDTO(customerOrder);

    }

    @Transactional
    public CustomerOrderDTO updateCustomerOrder(Long id, CustomerOrderDTO customerOrderDTO) {
        // in an order, you can only modify the products you want and the amount you want
        CustomerOrder existingOrder = customerOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        existingOrder.setQuantity(customerOrderDTO.getOrderProducts()
                .stream().mapToInt(ProductSummaryDTO::getQuantity).sum());

        double newTotalPrice = 0.0f;
        Set<OrderProduct> newOrderProducts = new HashSet<>();

        // process each product in the order
        for (ProductSummaryDTO productSummaryDTO : customerOrderDTO.getOrderProducts()) {
            Product product = productRepository.findById(productSummaryDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(productSummaryDTO.getProductId()));

            // we get every orderItem in existing order
            OrderProduct existingOrderProduct = existingOrder.getOrderItems().stream()
                    .filter(op -> op.getProduct().getId().equals(productSummaryDTO.getProductId()))
                    .findFirst().orElse(null);

            // if this product was already in the order, we check the difference in the quantity
            int quantityDifference = productSummaryDTO.getQuantity() -
                    (existingOrderProduct != null ? existingOrderProduct.getQuantity() : 0);

            // check if the product has enough stock
            if (product.getProductStock() < quantityDifference) {
                throw new BadOrderException("Not enough stock for product " + productSummaryDTO.getProductId());
            }

            // Update product stock
            product.setProductStock(product.getProductStock() - quantityDifference);
            productRepository.save(product);

            if (existingOrderProduct != null) {
                // update quantity in existing product
                existingOrderProduct.setQuantity(productSummaryDTO.getQuantity());
                newOrderProducts.add(existingOrderProduct);
            }
            else {// create order product
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrder(existingOrder);
                orderProduct.setProduct(product);
                orderProduct.setQuantity(productSummaryDTO.getQuantity());
                newOrderProducts.add(orderProduct);
                existingOrder.getOrderItems().add(orderProduct);
            }

            newTotalPrice += product.getProductPrice() * productSummaryDTO.getQuantity();
        }

        // remove any OrderProduct items that are no longer in the updated order
        existingOrder.getOrderItems().removeIf(op ->
                newOrderProducts.stream().noneMatch(newOp ->
                        newOp.getProduct().getId().equals(op.getProduct().getId())));

        existingOrder.setTotalPrice(newTotalPrice);

        CustomerOrder updatedOrder = customerOrderRepository.save(existingOrder);
        return toCustomerOrderDTO(updatedOrder);
    }

    public CustomerOrderDTO deleteCustomerOrder(Long id) {
        CustomerOrder order = customerOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        // restore product stock
        for (OrderProduct orderProduct : order.getOrderItems()) {
            Product product = orderProduct.getProduct();
            product.setProductStock(product.getProductStock() + orderProduct.getQuantity());
            productRepository.save(product);
        }
        CustomerOrderDTO deletedCustomerOrder = toCustomerOrderDTO(order);
        // delete the order
        customerOrderRepository.delete(order);

        return deletedCustomerOrder;
    }

    private ProductSummaryDTO toProductSummaryDTO(OrderProduct orderProduct) {
        ProductSummaryDTO productSummaryDTO = new ProductSummaryDTO();

        productSummaryDTO.setProductId(orderProduct.getProduct().getId());
        productSummaryDTO.setQuantity(orderProduct.getQuantity());

        return productSummaryDTO;
    }

    private CustomerOrderDTO toCustomerOrderDTO(CustomerOrder customerOrder) {
        CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO();
        customerOrderDTO.setId(customerOrder.getId());
        customerOrderDTO.setClientId(customerOrder.getClient().getId());

        Set<ProductSummaryDTO> orderProductDTOs = new HashSet<>();

        for (OrderProduct orderProduct : customerOrder.getOrderItems()) {
            orderProductDTOs.add(toProductSummaryDTO(orderProduct));
        }

        customerOrderDTO.setOrderProducts(orderProductDTOs);
        customerOrderDTO.setQuantity(customerOrder.getQuantity());
        customerOrderDTO.setTotalPrice(customerOrder.getTotalPrice());

        return customerOrderDTO;
    }

}
