package com.cms.service.dto;

import com.cms.domain.OrderStatus;
import com.cms.domain.User;
import java.time.LocalDateTime;
import java.util.List;

public class OrdersDto {

    private Long id;
    private User orderBy;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private List<ProductOrderDto> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(User orderBy) {
        this.orderBy = orderBy;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<ProductOrderDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrderDto> products) {
        this.products = products;
    }
}
