package com.intern.app.ecommerce.controller;

import com.intern.app.ecommerce.dto.AddToCartRequest;
import com.intern.app.ecommerce.dto.UpdateCartRequest;
import com.intern.app.ecommerce.model.AddToCartDetails;
import com.intern.app.ecommerce.service.AddToCartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class AddToCartController {

    private final AddToCartService cartService;

    public AddToCartController(AddToCartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public AddToCartDetails addToCart(@RequestBody AddToCartRequest request) {
        return cartService.addToCart(request);
    }

    @PutMapping("/update")
    public AddToCartDetails updateQuantity(@RequestBody UpdateCartRequest request) {
        return cartService.updateQuantity(request);
    }

    @DeleteMapping("/remove/{cartId}")
    public void remove(@PathVariable Long cartId) {
        cartService.removeFromCart(cartId);
    }

    @GetMapping("/user/{userId}")
    public List<AddToCartDetails> getUserCart(@PathVariable Long userId) {
        return cartService.getUserCart(userId);
    }
}