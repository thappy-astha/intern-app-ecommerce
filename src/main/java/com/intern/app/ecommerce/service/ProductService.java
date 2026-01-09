package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.model.Product;
import com.intern.app.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product getProductById(long id) {    //for get
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public byte[] getProductImage(long id) {
        byte[] img = getProductById(id).getImage();

        // This is the "log the length" part:
        System.out.println("Image length for product " + id + " = " +
                (img == null ? "null" : img.length));

        return img;
    }





    public Product saveProduct(
                                                //for post
            String name,
            int quantity,
            long discount,
            long originalPrice,
            long discountPrice,
            long size,
            String description,
            MultipartFile image
    ) throws Exception {

        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        product.setDiscount(discount);
        product.setOriginalPrice(originalPrice);
        product.setDiscountPrice(discountPrice);
        product.setSize(size);
        product.setDescription(description);
        product.setImage(image.getBytes());

        return productRepository.save(product);
    }



                                                         //for put
    public Product updateImage(long id, MultipartFile image) throws Exception {
        Product product = getProductById(id);
        product.setImage(image.getBytes());
        return productRepository.save(product);
    }
    public Product updateProduct(long id, Product updated) {
        Product existing = getProductById(id);

        existing.setName(updated.getName());
        existing.setQuantity(updated.getQuantity());
        existing.setDiscount(updated.getDiscount());
        existing.setOriginalPrice(updated.getOriginalPrice());
        existing.setDiscountPrice(updated.getDiscountPrice());
        existing.setSize(updated.getSize());
        existing.setDescription(updated.getDescription());

        return productRepository.save(existing);
    }






    public Product patchProduct(long id, Product updates) {  //for patch
        Product existing = getProductById(id);

        if (updates.getName() != null)
            existing.setName(updates.getName());

        if (updates.getDescription() != null)
            existing.setDescription(updates.getDescription());

        if (updates.getQuantity() >= 0)
            existing.setQuantity(updates.getQuantity());

        if (updates.getDiscount() >= 0)
            existing.setDiscount(updates.getDiscount());

        if (updates.getOriginalPrice() > 0)
            existing.setOriginalPrice(updates.getOriginalPrice());

        if (updates.getDiscountPrice() >= 0)
            existing.setDiscountPrice(updates.getDiscountPrice());

        if (updates.getSize() > 0)
            existing.setSize(updates.getSize());

        return productRepository.save(existing);
    }





    public void deleteById(Long id) {
        if (!productRepository.existsById(id))                   //to delete
        {
            throw new RuntimeException("product not found with id:" + id);
        }
        productRepository.deleteById(id);
    }

    public boolean exists(long id) {
        return productRepository.existsById(id);
    }





}
