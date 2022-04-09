package com.cms.controller;

import com.cms.domain.OrderStatus;
import com.cms.domain.Orders;
import com.cms.service.OrdersService;
import com.cms.service.dto.OrdersDto;
import com.cms.service.dto.SalesDto;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/get-user-orders")
    public List<OrdersDto> getAllUserOrders() {
        return ordersService.getAllOrdersByUser();
    }

    @PostMapping("/make-order")
    public OrdersDto makeOrder(@RequestBody OrdersDto orderDto) {
        return ordersService.makeOrders(orderDto);
    }

    @GetMapping("/get-all")
    public List<OrdersDto> getAllOrders() {
        return ordersService.getAllOrders();
    }

    @GetMapping("/update-status/{id}/{status}")
    public Boolean updateOrderStatus(@PathVariable("id") long id, @PathVariable("status") OrderStatus orderStatus) {
        ordersService.updateOrderStatus(id, orderStatus);
        return true;
    }

    @GetMapping("/get-order-status")
    public OrderStatus[] getOrderStatusTypes() {
        return ordersService.getOrderStatus();
    }

    @GetMapping("/get-product-sales")
    public List<SalesDto> getProductSales() {
        return ordersService.getProductSales();
    }
}
