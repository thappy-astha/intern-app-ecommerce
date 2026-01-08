package com.intern.app.ecommerce.controller;

import com.intern.app.ecommerce.model.Product;
import com.intern.app.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;



@RestController
@RequestMapping("/api/product")
@Validated

public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }




    @GetMapping("/{id}")
    public Product getProduct(@PathVariable long id) {
        return productService.getProductById(id);
    }
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable long id) {

        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(productService.getProductImage(id));
    }




    @PostMapping(consumes = "multipart/form-data")
    public Product addProduct(

            @RequestParam
            @NotBlank String name,

            @RequestParam
            @Min(0) Integer quantity,

            @RequestParam
            @Min(0)
            @Max(100) Long discount,

            @RequestParam
            @Positive long originalPrice,

            @RequestParam
            @PositiveOrZero long discountPrice,

            @RequestParam
            @Positive Long size,

            @RequestParam
            @Size(max = 500) String description,

            @RequestParam
            @NotNull MultipartFile image

    ) throws Exception {
        return productService.saveProduct(name,quantity,discount,originalPrice,discountPrice,size,description,image);
    }



    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable long id,
            @RequestBody Product product
    ) {
        return productService.updateProduct(id, product);
    }

    @PutMapping(value = "/{id}/image", consumes = "multipart/form-data")
    public Product updateProductImage(
            @PathVariable long id,
            @RequestParam MultipartFile image
    ) throws Exception {return productService.updateImage(id, image);

    }



    @PatchMapping("/{id}")
    public Product patchProduct(
            @PathVariable long id,
            @RequestBody Product product)
    {
        return productService.patchProduct(id, product);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }



}