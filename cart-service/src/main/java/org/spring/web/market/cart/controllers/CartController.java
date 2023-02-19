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
    public void addToCart(@PathVariable("id") Long id, @RequestAttribute("user") String user) {
        cartService.addToCart(id, user);
    }

    @GetMapping("/clear")
    public void resetCart(@RequestAttribute("user") String user) {
        cartService.resetCart(user);
    }

    @GetMapping("/remove/{id}")
    public void removeFromCart(@PathVariable("id") Long id, @RequestAttribute("user") String user) {
        cartService.removeFromCart(id, user);
    }

    @GetMapping
    @ResponseBody
    public CartDTO getCart(@RequestAttribute("user") String user) {
        return cartConverter.modelCartToDTO(cartService.getCurrentCart(user));
    }

    @GetMapping("/change_quantity")
    public void changeQuantity(@RequestParam Long productId, @RequestParam Integer delta, @RequestAttribute("user") String user) {
        cartService.changeQuantity(productId, delta, user);
    }

}
