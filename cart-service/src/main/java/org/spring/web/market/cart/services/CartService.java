package org.spring.web.market.cart.services;

import org.spring.web.market.api.dto.ProductDTO;
import org.spring.web.market.cart.integrations.ProductServiceIntegration;
import org.spring.web.market.cart.entities.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
public class CartService {
    //private HashMap<String, Cart> cartList;
    private Cart cart;
    private ProductServiceIntegration productServiceIntegration;

    public CartService() {
        //var cartList = new HashMap<String, Cart>();
        cart = new Cart();
    }

    @Autowired
    public void setProductService(ProductServiceIntegration productServiceIntegration) {
        this.productServiceIntegration = productServiceIntegration;
    }

    public Cart getCurrentCart() {
//        Cart cart = (Cart) session.getAttribute("cart");
//        if (cart == null) {
//            cart = new Cart();
//            session.setAttribute("cart", cart);
//        }
        return cart;
    }

    public void resetCart() {
        cart = new Cart();
    }

    public void addToCart(Long productId) {
        ProductDTO product = productServiceIntegration.getProductById(productId);
        //Cart cart = getCurrentCart(session);
        cart.add(product);
    }

    public void removeFromCart(Long productId) {
        ProductDTO product = productServiceIntegration.getProductById(productId);
        //Cart cart = getCurrentCart(session);
        cart.remove(product);
    }

    public void changeQuantity(Long productId, Integer delta) {
        ProductDTO product = productServiceIntegration.getProductById(productId);
        cart.changeQuantity(product, delta);
    }

}
