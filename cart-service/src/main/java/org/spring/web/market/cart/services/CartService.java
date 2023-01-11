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
    private HashMap<String, Cart> cartList;
    private ProductServiceIntegration productServiceIntegration;

    public CartService() {
        var cartList = new HashMap<String, Cart>();
    }

    @Autowired
    public void setProductService(ProductServiceIntegration productServiceIntegration) {
        this.productServiceIntegration = productServiceIntegration;
    }

    public Cart getCurrentCart(String user) {
        Cart cart = cartList.get(user);
        if ( cart==null) {
            cart = cartList.put(user, new Cart());
        }
        return cart;
    }

    public void resetCart(String user) {
        cartList.remove(user);
    }

    public void addToCart(Long productId, String user) {
        ProductDTO product = productServiceIntegration.getProductById(productId);
        Cart cart = getCurrentCart(user);
        cart.add(product);
    }

    public void removeFromCart(Long productId, String user) {
        ProductDTO product = productServiceIntegration.getProductById(productId);
        Cart cart = getCurrentCart(user);
        cart.remove(product);
    }

    public void changeQuantity(Long productId, Integer delta, String user) {
        ProductDTO product = productServiceIntegration.getProductById(productId);
        Cart cart = getCurrentCart(user);
        cart.changeQuantity(product, delta);
    }

}
