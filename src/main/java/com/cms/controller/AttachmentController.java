package com.cms.controller;

import com.cms.domain.Product;
import com.cms.repository.ProductRepository;
import java.util.Arrays;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class AttachmentController {

    @Autowired
    private ProductRepository productRepository;
    //    @GetMapping("/product-image/{id}")
    //    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws Exception {
    //
    //        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product image not found"));
    //
    //        return  ResponseEntity.ok()
    //            .contentType(MediaType.IMAGE_PNG)
    ////            .header(HttpHeaders.CONTENT_DISPOSITION,
    ////                "attachment; filename=\"" + "product-image"
    ////                    + "\"")
    //
    //            Byte[] data = product.getData();
    //            byte[] b = new byte[product.getData().length];
    //            Arrays.setAll(b, n -> data[n]);
    //
    //            ResponseEntity.ok().body(new ByteArrayResource()));
    //    }

    //    @PostMapping("/upload/{productId}")
    //    public String uploadFile(@PathParam ("productId") Long productId, @RequestParam("file") MultipartFile file throws Exception {
    //
    //        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("product not found"));
    //
    //        product.setData(file.getBytes());
    //
    //        productRepository.save(product);
    //
    //        String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
    //            .path("/product-image/")
    //            .path(product.getId().toString())
    //            .toUriString();
    //        return downloadURL;
    //    }

}
