package com.intern.app.ecommerce.service;

import com.intern.app.ecommerce.dto.AddToCartRequest;
import com.intern.app.ecommerce.dto.UpdateCartRequest;
import com.intern.app.ecommerce.model.AddToCartDetails;
import com.intern.app.ecommerce.model.Product;
import com.intern.app.ecommerce.model.User;
import com.intern.app.ecommerce.repository.AddToCartRepository;
import com.intern.app.ecommerce.repository.ProductRepository;
import com.intern.app.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddToCartService {

    private final AddToCartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public AddToCartService(AddToCartRepository cartRepository,
                            ProductRepository productRepository,
                            UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    //add to cart
    public AddToCartDetails addToCart(AddToCartRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        double totalPrice = product.getDiscountPrice().doubleValue() * request.getQuantity();

        AddToCartDetails cart = cartRepository
                .findByUserIdAndProductId(request.getUserId(), request.getProductId())
                .orElse(new AddToCartDetails());

        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(request.getQuantity());
        cart.setPrice(totalPrice);

        return cartRepository.save(cart);
    }

    //update the quantity
    public AddToCartDetails updateQuantity(UpdateCartRequest request) {

        AddToCartDetails cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = cart.getProduct();

        double totalPrice = product.getDiscountPrice().doubleValue() * request.getQuantity();

        cart.setQuantity(request.getQuantity());
        cart.setPrice(totalPrice);

        return cartRepository.save(cart);
    }


    public List<AddToCartDetails> getUserCart(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void removeFromCart(Long cartId) {
        AddToCartDetails cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cartRepository.delete(cart);
    }
}