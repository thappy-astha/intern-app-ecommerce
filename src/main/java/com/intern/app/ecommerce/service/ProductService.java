package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.model.Product;
import com.intern.app.ecommerce.model.ProductImage;
import com.intern.app.ecommerce.model.Vendor;
import com.intern.app.ecommerce.repository.ProductRepository;
import com.intern.app.ecommerce.repository.ProductImageRepository;
import com.intern.app.ecommerce.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;
    private final VendorRepository vendorRepository;

    public ProductService(ProductRepository productRepository,
                          ProductImageRepository imageRepository,
                          VendorRepository vendorRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.vendorRepository = vendorRepository;
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
            Long vendorId,
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
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setSizes(sizes);
        product.setQuantity(quantity);
        product.setDiscount(discount);
        product.setOriginalPrice(originalPrice);
        product.setDiscountPrice(discountPrice);
        product.setDescription(description);
        product.setVendor(vendor);

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

        if (name != null) product.setName(name);
        if (category != null) product.setCategory(category);
        if (sizes != null) product.setSizes(sizes);
        if (quantity != null) product.setQuantity(quantity);
        if (discount != null) product.setDiscount(discount);
        if (originalPrice != null) product.setOriginalPrice(originalPrice);
        if (discountPrice != null) product.setDiscountPrice(discountPrice);
        if (description != null) product.setDescription(description);


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

