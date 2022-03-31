package com.cms.controller;

import com.cms.domain.Product;
import com.cms.service.ProductService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/get-all")
    public List<Product> getAllProducts() {
        return productService.getAllProduct();
    }

    @PostMapping("/save")
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PostMapping("/update")
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @GetMapping("/delete/{id}")
    public Product deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }
}
