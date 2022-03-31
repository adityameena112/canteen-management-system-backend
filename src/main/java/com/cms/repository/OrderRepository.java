package com.cms.repository;

import com.cms.domain.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query("SELECT o FROM Orders o WHERE o.orderBy.id = :id")
    List<Orders> getOrdersByUser(@Param("id") Long id);

    @Query("SELECT o FROM Orders o order by o.orderDate desc")
    List<Orders> getAllOrders();
}
