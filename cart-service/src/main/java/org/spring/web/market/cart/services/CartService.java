package org.spring.web.market.cart.services;

import org.spring.web.market.api.dto.ProductDTO;
import org.spring.web.market.cart.integrations.ProductServiceIntegration;
import org.spring.web.market.cart.entities.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class CartService {
    private ProductServiceIntegration productServiceIntegration;

    @Autowired
    public void setProductService(ProductServiceIntegration productServiceIntegration) {
        this.productServiceIntegration = productServiceIntegration;
    }

    public Cart getCurrentCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public void resetCart(HttpSession session) {
        session.removeAttribute("cart");
    }

    public void addToCart(HttpSession session, Long productId) {
        Optional<ProductDTO> product = productServiceIntegration.getProductById(productId);
        product.ifPresent(productDTO -> addToCart(session, productDTO));
    }

    public void addToCart(HttpSession session, ProductDTO product) {
        Cart cart = getCurrentCart(session);
        cart.add(product);
    }

    public void removeFromCart(HttpSession session, Long productId) {
        Optional<ProductDTO> product = productServiceIntegration.getProductById(productId);
        product.ifPresent(productDTO -> removeFromCart(session, productDTO));
    }

    public void removeFromCart(HttpSession session, ProductDTO product) {
        Cart cart = getCurrentCart(session);
        cart.remove(product);
    }

    public void setProductCount(HttpSession session, Long productId, Long quantity) {
        Cart cart = getCurrentCart(session);
        Optional<ProductDTO> product = productServiceIntegration.getProductById(productId);
        product.ifPresent(productDTO -> cart.setQuantity(productDTO, quantity));
    }

    public void setProductCount(HttpSession session, ProductDTO product, Long quantity) {
        Cart cart = getCurrentCart(session);
        cart.setQuantity(product, quantity);
    }

    public double getTotalCost(HttpSession session) {
        return getCurrentCart(session).getTotalCost();
    }
}
