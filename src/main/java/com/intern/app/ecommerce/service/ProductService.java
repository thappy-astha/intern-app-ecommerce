package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.model.Product;
import com.intern.app.ecommerce.model.ProductImage;
import com.intern.app.ecommerce.repository.ProductRepository;
import com.intern.app.ecommerce.repository.ProductImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;

    public ProductService(ProductRepository productRepository,
                          ProductImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public Product getProductById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }




        public List<String> getImagesByProductId(long productId) {

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            List<String> images = new ArrayList<>();

            if (product.getImages() != null) {
                for (ProductImage productImage : product.getImages()) {
                    String base64 = Base64.getEncoder()
                            .encodeToString(productImage.getImage());
                    images.add(base64);
                }
            }

            return images;
        }




    public Product saveProduct(
            String name,
            String category,
            String sizes,
            Integer quantity,
            Long discount,
            BigDecimal originalPrice,
            BigDecimal discountPrice,
            String description,
            MultipartFile[] images
    ) throws Exception {

        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setSizes(sizes);
        product.setQuantity(quantity);
        product.setDiscount(discount);
        product.setOriginalPrice(originalPrice);
        product.setDiscountPrice(discountPrice);
        product.setDescription(description);

        Product savedProduct = productRepository.save(product);

        List<ProductImage> imageList = new ArrayList<>();
        for (MultipartFile file : images) {
            imageList.add(new ProductImage(file.getBytes(), savedProduct));
        }

        imageRepository.saveAll(imageList);
        savedProduct.setImages(imageList);

        return savedProduct;
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
