package com.cms.service;

import com.cms.domain.OrderProducts;
import com.cms.domain.OrderStatus;
import com.cms.domain.Orders;
import com.cms.domain.Product;
import com.cms.repository.OrderProductsRepository;
import com.cms.repository.OrderRepository;
import com.cms.repository.ProductRepository;
import com.cms.repository.UserRepository;
import com.cms.service.dto.OrdersDto;
import com.cms.service.dto.ProductOrderDto;
import com.cms.service.dto.SalesDto;
import com.cms.utility.CustomMailSenderUtility;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private OrderProductsRepository orderProductsRepository;

    @Autowired
    private CustomMailSenderUtility customMailSenderUtility;

    public OrdersDto makeOrders(OrdersDto ordersDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = (User) authentication.getPrincipal();

        com.cms.domain.User user = userRepository.findOneByLogin(u.getUsername()).orElse(null);

        Orders order = new Orders();
        order.setOrderStatus(OrderStatus.BOOKED);
        order.setOrderBy(user);
        order.setOrderDate(LocalDateTime.now());
        Orders o = orderRepository.save(order);

        List<OrderProducts> orderProductsList = new ArrayList<>();
        ordersDto
            .getProducts()
            .forEach(x -> {
                OrderProducts orderProducts = new OrderProducts();
                orderProducts.setOrder(o);
                orderProducts.setQuantity(x.getQuantity());
                orderProducts.setProduct(x.getProduct());
                Product p = productRepository.findById(x.getProduct().getId()).get();
                p.setRemainingQuantity(p.getRemainingQuantity() - x.getQuantity());
                productRepository.save(p);
                orderProductsList.add(orderProducts);
            });

        Double grandTotal = ordersDto
            .getProducts()
            .stream()
            .map(x -> x.getQuantity() * x.getProduct().getPrice())
            .reduce(0.0, (left, right) -> left + right);

        orderProductsRepository.saveAll(orderProductsList);

        String body = "<h1>Your order has been placed</h1>";
        body += "<h2>Order id: " + o.getId() + "</h2>";
        body += "<table><tr><th>id</th><th>name</th><th>quantity</th><th>total</th></tr>";

        for (ProductOrderDto p : ordersDto.getProducts()) {
            body +=
                "<tr><td>" +
                p.getProduct().getId() +
                "</td><td>" +
                p.getProduct().getProductName() +
                "</td><td>" +
                p.getQuantity() +
                "</td><td>" +
                p.getTotalPrice() +
                "</td></tr>";
        }

        body += "</table>";
        body += "<h3>Total: " + grandTotal + "</h3>";
        body += "<h3>SGST: " + (grandTotal / 100.0) * 2.5 + "</h3>";
        body += "<h3>GST: " + (grandTotal / 100.0) * 2.5 + "</h3>";
        body += "<h3>Grand Total: " + (grandTotal + ((grandTotal / 100.0) * 2.5) * 2) + "</h3>";
        customMailSenderUtility.sendMail(user.getEmail(), "Team Pranzo", body, new HashMap<>(), null, null);

        return ordersDto;
    }

    public List<OrdersDto> getAllOrdersByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = (User) authentication.getPrincipal();
        com.cms.domain.User user = userRepository.findOneByLogin(u.getUsername()).orElse(null);

        List<Orders> orders = orderRepository.getOrdersByUser(user.getId());

        List<OrdersDto> ordersDtos = new ArrayList<>();
        orders
            .stream()
            .forEach(x -> {
                OrdersDto ordersDto = new OrdersDto();
                ordersDto.setId(x.getId());
                ordersDto.setOrderBy(x.getOrderBy());
                ordersDto.setOrderDate(x.getOrderDate());
                ordersDto.setOrderStatus(x.getOrderStatus());
                List<ProductOrderDto> productOrderDtos = x
                    .getOrderProducts()
                    .stream()
                    .map(y -> {
                        ProductOrderDto productOrderDto = new ProductOrderDto();
                        productOrderDto.setProduct(y.getProduct());
                        productOrderDto.setTotalPrice(y.getProduct().getPrice() * y.getQuantity());
                        productOrderDto.setQuantity(y.getQuantity());

                        return productOrderDto;
                    })
                    .collect(Collectors.toList());
                ordersDto.setProducts(productOrderDtos);
                ordersDtos.add(ordersDto);
            });
        return ordersDtos;
    }

    public List<OrdersDto> getAllOrders() {
        List<Orders> orders = orderRepository.getAllOrders();

        List<OrdersDto> ordersDtos = new ArrayList<>();
        orders
            .stream()
            .forEach(x -> {
                OrdersDto ordersDto = new OrdersDto();
                ordersDto.setId(x.getId());
                ordersDto.setOrderBy(x.getOrderBy());
                ordersDto.setOrderDate(x.getOrderDate());
                ordersDto.setOrderStatus(x.getOrderStatus());
                List<ProductOrderDto> productOrderDtos = x
                    .getOrderProducts()
                    .stream()
                    .map(y -> {
                        ProductOrderDto productOrderDto = new ProductOrderDto();
                        productOrderDto.setProduct(y.getProduct());
                        productOrderDto.setTotalPrice(y.getProduct().getPrice() * y.getQuantity());
                        productOrderDto.setQuantity(y.getQuantity());

                        return productOrderDto;
                    })
                    .collect(Collectors.toList());
                ordersDto.setProducts(productOrderDtos);
                ordersDtos.add(ordersDto);
            });
        return ordersDtos;
    }

    public void updateOrderStatus(Long id, OrderStatus orderStatus) {
        Orders order = orderRepository.findById(id).orElse(null);

        if (order != null) {
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
        }
        //        return order;
    }

    public List<SalesDto> getProductSales() {
        Map<Long, SalesDto> result = new HashMap<>();
        List<Orders> orders = orderRepository.findAll();
        orders
            .stream()
            .forEach(order -> {
                order
                    .getOrderProducts()
                    .stream()
                    .forEach(orderProduct -> {
                        SalesDto dto = new SalesDto();

                        if (result.containsKey(orderProduct.getProduct().getId())) {
                            dto = result.get(orderProduct.getProduct().getId());
                            dto.setSales(orderProduct.getQuantity() + dto.getSales());
                        } else {
                            dto.setSales(orderProduct.getQuantity());
                            dto.setProductName(orderProduct.getProduct().getProductName());
                        }
                        result.put(orderProduct.getProduct().getId(), dto);
                    });
            });
        return new ArrayList<>(result.values());
    }

    public OrderStatus[] getOrderStatus() {
        return OrderStatus.values();
    }
}
