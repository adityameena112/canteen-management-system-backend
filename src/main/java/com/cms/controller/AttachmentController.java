package com.cms.controller;

import com.cms.domain.Product;
import com.cms.repository.ProductRepository;
import java.io.IOException;
import java.util.Arrays;
import javax.websocket.server.PathParam;
import org.apache.commons.compress.utils.IOUtils;
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
    //        return ResponseEntity.ok()
    //            .contentType(MediaType.IMAGE_PNG)
    //            .header(HttpHeaders.CONTENT_DISPOSITION,
    //                "attachment; filename=\"" + "product-image"
    //                    + "\"")
    //            .body(new ByteArrayResource(toPrimitives(product.getData())));
    //    }

    @GetMapping(value = "/product-image", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType(@RequestParam("id") Long id) throws IOException {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product image not found"));
        return toPrimitives(product.getData());
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file) throws IOException {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found"));

        product.setData(toObjects(file.getBytes()));

        productRepository.save(product);

        String downloadURl = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/product-image/")
            .path(product.getId().toString())
            .toUriString();
        return downloadURl;
    }

    public byte[] toPrimitives(Byte[] oBytes) {
        byte[] bytes = new byte[oBytes.length];
        for (int i = 0; i < oBytes.length; i++) {
            bytes[i] = oBytes[i];
        }
        return bytes;
    }

    Byte[] toObjects(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        int i = 0;
        for (byte b : bytesPrim) bytes[i++] = b; //Autoboxing
        return bytes;
    }
}
