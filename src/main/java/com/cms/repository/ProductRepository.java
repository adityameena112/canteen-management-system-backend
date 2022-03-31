package com.cms.repository;

import com.cms.domain.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p from Product p where p.deleted = null")
    List<Product> getAllProduct();
}
