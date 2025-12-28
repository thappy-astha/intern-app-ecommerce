package com.intern.app.ecommerce.controller;

import com.intern.app.ecommerce.model.Product;
import com.intern.app.ecommerce.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductRepository productRepository;

    //com.intern.app.ecommerce
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable long id) {

        return productRepository.findById(id).orElse(null);
    }


    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable long id,
            @RequestBody Product product) {

        product.setId(id);
        return productRepository.save(product);
    }



    @PatchMapping("/{id}")
    public Product patchProduct(
            @PathVariable long id,
            @RequestBody Product product)
    {
        Product existing = productRepository.findById(id).orElse(null);

        if(existing == null){
            return null;
        }


        if (product.getName() != null)
            existing.setName(product.getName());
        if (product.getQuantity() != 0)
            existing.setQuantity(product.getQuantity());
        if (product.getDiscount() != 0)
            existing.setDiscount(product.getDiscount());
        if (product.getOriginalPrice() != 0)
            existing.setOriginalPrice(product.getOriginalPrice());
        if (product.getDiscountPrice() != 0)
            existing.setDiscountPrice(product.getDiscountPrice());
        if (product.getSize() != 0)
            existing.setSize(product.getSize());
        if (product.getDescription() != null)
            existing.setDescription(product.getDescription());

        return productRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "Product deleted successfully";
    }



    @HeadMapping("/{id}")
    public ResponseEntity<Void> headProduct(@PathVariable long id) {
        boolean exists = productRepository.existsById(id);
        return exists ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();

    }



}