package com.cms.service;

import com.cms.domain.Product;
import com.cms.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProduct() {
        List<Product> products = productRepository.getAllProduct();
        products
            .stream()
            .forEach(product -> {
                String imageUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/product-image")
                    .queryParam("id", product.getId())
                    .toUriString();

                product.setImageUrl(imageUrl);
            });

        return products;
    }

    public Product saveProduct(Product product) {
        product.setId(null);
        product.setCreatedDate(LocalDateTime.now());
        product.setLastModifiedDate(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        Product p = productRepository.findById(product.getId()).orElse(null);
        if (p != null) {
            p.setProductName(product.getProductName());
            p.setPrice(product.getPrice());
            p.setDescription(product.getDescription());
            product = productRepository.save(p);
        }
        return product;
    }

    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setDeleted(true);
            productRepository.save(product);
        }
        return product;
    }
}
