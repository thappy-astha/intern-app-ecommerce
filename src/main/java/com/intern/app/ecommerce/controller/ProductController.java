package com.intern.app.ecommerce.controller;

import com.intern.app.ecommerce.model.Product;
import com.intern.app.ecommerce.model.ProductImage;
import com.intern.app.ecommerce.repository.ProductImageRepository;
import com.intern.app.ecommerce.service.ProductService;
import jakarta.validation.constraints.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final ProductImageRepository imageRepository;

    public ProductController(ProductService productService,
                             ProductImageRepository imageRepository) {
        this.productService = productService;
        this.imageRepository = imageRepository;
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

    @GetMapping("/image/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable long imageId) {

        ProductImage image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getImageData());
    }





    @PostMapping(consumes = "multipart/form-data")
    public Product addProduct(
            @RequestParam Long vendorId,
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
                vendorId, name, category, sizes,
                quantity, discount, originalPrice,
                discountPrice, description, images
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }


    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Product> updateProduct(
            @PathVariable long id,
            @RequestParam String name,
            @RequestParam String category,
            @RequestParam String sizes,
            @RequestParam Integer quantity,
            @RequestParam Long discount,
            @RequestParam BigDecimal originalPrice,
            @RequestParam BigDecimal discountPrice,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile[] images
    ) throws Exception {

        return ResponseEntity.ok(
                productService.updateProduct(
                        id, name, category, sizes, quantity,
                        discount, originalPrice, discountPrice,
                        description, images
                )
        );
    }








    @PatchMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Product> patchProduct(
            @PathVariable long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String sizes,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Long discount,
            @RequestParam(required = false) BigDecimal originalPrice,
            @RequestParam(required = false) BigDecimal discountPrice,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) MultipartFile[] images
    ) throws Exception {

        return ResponseEntity.ok(
                productService.patchProduct(id, name, category, sizes,
                        quantity, discount, originalPrice,
                        discountPrice, description, images)
        );
    }

}
