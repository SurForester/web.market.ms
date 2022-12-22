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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;
    private final CartConverter cartConverter;

    @Autowired
    public void setShoppingCartService(CartService shoppingCartService) {
        this.cartService = shoppingCartService;
    }

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
    @ResponseBody
    public CartDTO cartPage(HttpSession httpSession) {
        Cart cart = cartService.getCurrentCart(httpSession);
        return cartConverter.modelCartToDTO(cart);
    }
}
