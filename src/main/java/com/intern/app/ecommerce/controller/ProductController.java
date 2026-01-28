package com.intern.app.ecommerce.controller;

import com.intern.app.ecommerce.model.Product;
import com.intern.app.ecommerce.service.ProductService;
import jakarta.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }


    @GetMapping("/images/product/{productId}")
    public ResponseEntity<List<String>> getImagesByProductId(
            @PathVariable long productId) {

        return ResponseEntity.ok(productService.getImagesByProductId(productId));
    }



    @PostMapping(consumes = "multipart/form-data")
    public Product addProduct(
            @RequestParam @NotBlank String name,
            @RequestParam @NotBlank String category,
            @RequestParam String sizes,
            @RequestParam @Min(0) Integer quantity,
            @RequestParam @Min(0) @Max(100) Long discount,
            @RequestParam @Positive BigDecimal originalPrice,
            @RequestParam @PositiveOrZero BigDecimal discountPrice,
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
