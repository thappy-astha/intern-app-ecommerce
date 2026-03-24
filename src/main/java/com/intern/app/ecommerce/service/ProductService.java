package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.model.Product;
import com.intern.app.ecommerce.model.ProductImage;
import com.intern.app.ecommerce.model.Vendor;
import com.intern.app.ecommerce.repository.ProductImageRepository;
import com.intern.app.ecommerce.repository.ProductRepository;
import com.intern.app.ecommerce.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final VendorRepository vendorRepository;

    public ProductService(ProductRepository productRepository,
                          ProductImageRepository productImageRepository,
                          VendorRepository vendorRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.vendorRepository = vendorRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public List<Product> getProductsByVendor(Long vendorId) {
        return productRepository.findByVendorId(vendorId);
    }

    public List<String> getImagesByProductId(long productId) {
        Product product = getProductById(productId);
        List<String> imageUrls = new ArrayList<>();

        if (product.getImages() != null) {
            for (ProductImage image : product.getImages()) {
                imageUrls.add("/api/product/image/" + image.getId());
            }
        }

        return imageUrls;
    }

    public Product saveProduct(Long vendorId,
                               String name,
                               String category,
                               String sizes,
                               Integer quantity,
                               Long discount,
                               BigDecimal originalPrice,
                               BigDecimal discountPrice,
                               String description,
                               MultipartFile[] images) throws Exception {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + vendorId));

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

        if (quantity != null && quantity == 0) {
            product.setStatus("SOLDOUT");
        } else {
            product.setStatus("AVAILABLE");
        }

        Product savedProduct = productRepository.save(product);

        if (images != null) {
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : images) {
                if (file != null && !file.isEmpty()) {
                    ProductImage image = new ProductImage();
                    image.setProduct(savedProduct);

                    image.setContentType(file.getContentType());
                    image.setImageData(file.getBytes());
                    productImages.add(image);
                }
            }
            productImageRepository.saveAll(productImages);
            savedProduct.setImages(productImages);
        }

        return savedProduct;
    }

    public Product updateProduct(long id,
                                 String name,
                                 String category,
                                 String sizes,
                                 Integer quantity,
                                 Long discount,
                                 BigDecimal originalPrice,
                                 BigDecimal discountPrice,
                                 String description,
                                 MultipartFile[] images) throws Exception {

        Product product = getProductById(id);

        product.setName(name);
        product.setCategory(category);
        product.setSizes(sizes);
        product.setQuantity(quantity);
        product.setDiscount(discount);
        product.setOriginalPrice(originalPrice);
        product.setDiscountPrice(discountPrice);
        product.setDescription(description);

        if (quantity != null && quantity == 0) {
            product.setStatus("SOLDOUT");
        } else {
            product.setStatus("AVAILABLE");
        }

        Product updatedProduct = productRepository.save(product);

        if (images != null && images.length > 0) {
            if (product.getImages() != null) {
                product.getImages().clear();
            }

            List<ProductImage> newImages = new ArrayList<>();
            for (MultipartFile file : images) {
                if (file != null && !file.isEmpty()) {
                    ProductImage image = new ProductImage();
                    image.setProduct(updatedProduct);
                    //image.setFileName(file.getOriginalFilename());
                    image.setContentType(file.getContentType());
                    image.setImageData(file.getBytes());
                    newImages.add(image);
                }
            }

            productImageRepository.saveAll(newImages);
            updatedProduct.setImages(newImages);
        }

        return updatedProduct;
    }

    public Product patchProduct(long id,
                                String name,
                                String category,
                                String sizes,
                                Integer quantity,
                                Long discount,
                                BigDecimal originalPrice,
                                BigDecimal discountPrice,
                                String description,
                                MultipartFile[] images) throws Exception {

        Product product = getProductById(id);

        if (name != null) product.setName(name);
        if (category != null) product.setCategory(category);
        if (sizes != null) product.setSizes(sizes);
        if (quantity != null) product.setQuantity(quantity);
        if (discount != null) product.setDiscount(discount);
        if (originalPrice != null) product.setOriginalPrice(originalPrice);
        if (discountPrice != null) product.setDiscountPrice(discountPrice);
        if (description != null) product.setDescription(description);

        Integer finalQty = product.getQuantity();
        if (finalQty != null && finalQty == 0) {
            product.setStatus("SOLDOUT");
        } else {
            product.setStatus("AVAILABLE");
        }

        Product patchedProduct = productRepository.save(product);

        if (images != null && images.length > 0) {
            List<ProductImage> newImages = new ArrayList<>();
            for (MultipartFile file : images) {
                if (file != null && !file.isEmpty()) {
                    ProductImage image = new ProductImage();
                    image.setProduct(patchedProduct);
                   // image.setFileName(file.getOriginalFilename());
                    image.setContentType(file.getContentType());
                    image.setImageData(file.getBytes());
                    newImages.add(image);
                }
            }

            productImageRepository.saveAll(newImages);

            List<ProductImage> existingImages = patchedProduct.getImages();
            if (existingImages == null) {
                existingImages = new ArrayList<>();
            }
            existingImages.addAll(newImages);
            patchedProduct.setImages(existingImages);
        }

        return patchedProduct;
    }

    public void deleteProduct(long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    // NEW METHOD
    public Product reduceStock(Long productId, Integer orderedQty) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        int currentQty = product.getQuantity() != null ? product.getQuantity() : 0;

        if (orderedQty == null || orderedQty <= 0) {
            throw new RuntimeException("Ordered quantity must be greater than 0");
        }

        if (orderedQty > currentQty) {
            throw new RuntimeException("Not enough stock for product id: " + productId);
        }

        int newQty = currentQty - orderedQty;
        product.setQuantity(newQty);

        if (newQty == 0) {
            product.setStatus("SOLDOUT");
        } else {
            product.setStatus("AVAILABLE");
        }

        return productRepository.save(product);
    }
}