package com.intern.app.ecommerce.controller;

import com.intern.app.ecommerce.model.Product;
import com.intern.app.ecommerce.service.ProductService;
import jakarta.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping("/{id}")
    public Product getProduct(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable long imageId) {
        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(productService.getImage(imageId));
    }

    @PostMapping(consumes = "multipart/form-data")
    public Product addProduct(
            @RequestParam @NotBlank String name,
            @RequestParam @NotBlank String category,
            @RequestParam List<String> sizes,
            @RequestParam @Min(0) Integer quantity,
            @RequestParam @Min(0) @Max(100) Long discount,
            @RequestParam @Positive long originalPrice,
            @RequestParam @PositiveOrZero long discountPrice,
            @RequestParam String description,
            @RequestParam MultipartFile[] images
    ) throws Exception {

        return productService.saveProduct(
                name, category, sizes, quantity,
                discount, originalPrice,
                discountPrice, description, images
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted");
    }
}
