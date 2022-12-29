package org.spring.web.market.controllers;

import lombok.RequiredArgsConstructor;
import org.spring.web.market.api.dto.CartDTO;
import org.spring.web.market.integrations.CartServiceIntegration;
//import org.spring.web.market.services.ShoppingCartService;
//import org.spring.web.market.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartServiceIntegration cartServiceIntegration;

    @GetMapping
    public String cartPage(Model model, HttpSession httpSession) {
        CartDTO cart = cartServiceIntegration.getCart(httpSession);
        model.addAttribute("cart", cart);
        return "cart-page";
    }

}
