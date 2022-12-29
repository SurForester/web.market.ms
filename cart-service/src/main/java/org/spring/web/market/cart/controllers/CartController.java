package org.spring.web.market.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.spring.web.market.api.dto.CartDTO;
import org.spring.web.market.cart.converters.CartConverter;
import org.spring.web.market.cart.converters.CartItemConverter;
import org.spring.web.market.cart.services.CartService;
import org.spring.web.market.cart.entities.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {
    private CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable("id") Long id, HttpSession httpSession) {
        cartService.addToCart(httpSession, id);
    }

    @GetMapping("/clear")
    public void resetCart(HttpSession httpSession) {
        cartService.resetCart(httpSession);
    }

    @GetMapping("/remove/{id}")
    public void removeFromCart(@PathVariable("id") Long id, HttpSession httpSession) {
        cartService.removeFromCart(httpSession, id);
    }

    @GetMapping
    public CartDTO getCart(HttpSession httpSession) {
        Cart cart = cartService.getCurrentCart(httpSession);
        return cartConverter.modelCartToDTO(cart);
    }

}
