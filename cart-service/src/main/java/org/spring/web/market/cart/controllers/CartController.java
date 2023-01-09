package org.spring.web.market.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.spring.web.market.api.dto.CartDTO;
import org.spring.web.market.cart.converters.CartConverter;
import org.spring.web.market.cart.services.CartService;
import org.spring.web.market.cart.entities.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping
@CrossOrigin("*")
public class CartController {
    private CartService cartService;
    private final CartConverter cartConverter;

    @Autowired
    public CartController(CartService cartService, CartConverter cartConverter) {
        this.cartService = cartService;
        this.cartConverter = cartConverter;
    }

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable("id") Long id) {
        cartService.addToCart(id);
    }

    @GetMapping("/clear")
    public void resetCart() {
        cartService.resetCart();
    }

    @GetMapping("/remove/{id}")
    public void removeFromCart(@PathVariable("id") Long id) {
        cartService.removeFromCart(id);
    }

    @GetMapping
    @ResponseBody
    public CartDTO getCart() {
        return cartConverter.modelCartToDTO(cartService.getCurrentCart());
    }

    @GetMapping("/change_quantity")
    public void changeQuantity(@RequestParam Long productId, @RequestParam Integer delta) {
        cartService.changeQuantity(productId, delta);
    }

}
