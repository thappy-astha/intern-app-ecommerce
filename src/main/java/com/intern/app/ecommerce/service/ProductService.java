package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.model.Product;
import com.intern.app.ecommerce.model.ProductImage;
import com.intern.app.ecommerce.repository.ProductRepository;
import com.intern.app.ecommerce.repository.ProductImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
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

        List<ProductImage> images =
                imageRepository.findByProductId(productId);

        return images.stream()
                .map(img -> "/api/product/image/" + img.getId())
                .toList();
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
            ProductImage img = new ProductImage(
                    file.getBytes(),          // 👈 image as bytes
                    file.getContentType(),    // 👈 image/png, image/jpeg
                    savedProduct
            );
            imageList.add(img);
        }

        imageRepository.saveAll(imageList);
        savedProduct.setImages(imageList);

        return savedProduct;
    }


        public void deleteProduct(long id) {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            productRepository.delete(product);
        }






    public Product updateProduct(
            long id,
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

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(name);
        product.setCategory(category);
        product.setSizes(sizes);
        product.setQuantity(quantity);
        product.setDiscount(discount);
        product.setOriginalPrice(originalPrice);
        product.setDiscountPrice(discountPrice);
        product.setDescription(description);

        //If images are sent → replace old ones
        if (images != null && images.length > 0) {
            product.getImages().clear(); // orphanRemoval deletes old images

            for (MultipartFile file : images) {
                ProductImage img = new ProductImage();
                img.setImageData(file.getBytes());
                img.setContentType(file.getContentType());
                img.setProduct(product);

                product.getImages().add(img);
            }

        }

        return productRepository.save(product);
    }









    public Product patchProduct(
            long id,
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

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (name != null) product.setName(name);
        if (category != null) product.setCategory(category);
        if (sizes != null) product.setSizes(sizes);
        if (quantity != null) product.setQuantity(quantity);
        if (discount != null) product.setDiscount(discount);
        if (originalPrice != null) product.setOriginalPrice(originalPrice);
        if (discountPrice != null) product.setDiscountPrice(discountPrice);
        if (description != null) product.setDescription(description);

        //Update images ONLY if provided
        if (images != null && images.length > 0) {
            product.getImages().clear();

            List<ProductImage> newImages = new ArrayList<>();
            for (MultipartFile file : images) {
                ProductImage img = new ProductImage();
                img.setImageData(file.getBytes());
                img.setContentType(file.getContentType());
                img.setProduct(product);
                newImages.add(img);
            }
            product.setImages(newImages);
        }

        return productRepository.save(product);
    }

}

