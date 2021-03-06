package com.cms.service;

import com.cms.domain.*;
import com.cms.repository.OrderProductsRepository;
import com.cms.repository.OrderRepository;
import com.cms.repository.ProductRepository;
import com.cms.repository.UserRepository;
import com.cms.service.dto.OrdersDto;
import com.cms.service.dto.ProductOrderDto;
import com.cms.service.dto.SalesDto;
import com.cms.utility.CustomMailSenderUtility;
import java.time.LocalDateTime;
import java.util.*;
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
        order.setPaymentType(ordersDto.getPaymentType());

        if (Objects.equals(ordersDto.getPaymentType(), PaymentType.CASH)) {
            order.setPaymentStatus(PaymentStatus.PENDING);
        } else {
            order.setPaymentStatus(PaymentStatus.COMPLETE);
        }
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

        Double grandTotal = ordersDto.getProducts().stream().map(x -> x.getQuantity() * x.getProduct().getPrice()).reduce(0.0, Double::sum);

        orderProductsRepository.saveAll(orderProductsList);

        String body = "<h1>Your order has been placed</h1>";
        body += "<h2>Order id: " + o.getId() + "</h2>";
        body +=
            "<table style='font-family: arial, sans-serif;border-collapse: collapse;width: 50%;'><tr><th style='border: 1px solid #dddddd;text-align: left;padding: 8px;'>id</th><th style='border: 1px solid #dddddd;text-align: left;padding: 8px;'>name</th><th style='border: 1px solid #dddddd;text-align: left;padding: 8px;'>quantity</th><th style='border: 1px solid #dddddd;text-align: left;padding: 8px;'>total</th></tr>";

        for (ProductOrderDto p : ordersDto.getProducts()) {
            body +=
                "<tr><td>" +
                p.getProduct().getId() +
                "</td style='border: 1px solid #dddddd;text-align: left;padding: 8px;'><td>" +
                p.getProduct().getProductName() +
                "</td style='border: 1px solid #dddddd;text-align: left;padding: 8px;'><td>" +
                p.getQuantity() +
                "</td style='border: 1px solid #dddddd;text-align: left;padding: 8px;'><td>" +
                p.getTotalPrice() +
                "</td style='border: 1px solid #dddddd;text-align: left;padding: 8px;'></tr>";
        }

        body += "</table>";
        body += "<h4>Total: " + grandTotal + "</h3>";
        body += "<h4>SGST 2.5%: " + (grandTotal / 100.0) * 2.5 + "</h3>";
        body += "<h4>CGST 2.5%: " + (grandTotal / 100.0) * 2.5 + "</h3>";
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
                ordersDto.setPaymentStatus(x.getPaymentStatus());
                ordersDto.setPaymentType(x.getPaymentType());
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
                ordersDto.setPaymentStatus(x.getPaymentStatus());
                ordersDto.setPaymentType(x.getPaymentType());
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
            if (Objects.equals(orderStatus, OrderStatus.COMPLETED) && Objects.equals(order.getPaymentStatus(), PaymentStatus.PENDING)) {
                order.setPaymentStatus(PaymentStatus.COMPLETE);
            }
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
