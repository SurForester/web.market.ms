package org.spring.web.market.cart.entities;

import lombok.Data;
import org.spring.web.market.api.dto.CartItemDTO;
import org.spring.web.market.api.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private Double totalCost;

    public Cart() {
        items = new ArrayList<>();
        totalCost = 0.0;
    }

    public void add(ProductDTO product) {
        CartItem cartItem = findOrderFromProduct(product);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setItemPrice(product.getPrice());
            cartItem.setQuantity(0L);
            cartItem.setId(0L);
            cartItem.setTotalPrice(0.0);
            items.add(cartItem);
        }
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        recalculate();
    }

    public void setQuantity(ProductDTO product, Long quantity) {
        CartItem cartItem = findOrderFromProduct(product);
        if (cartItem == null) {
            return;
        }
        cartItem.setQuantity(quantity);
        recalculate();
    }

    public void remove(ProductDTO product) {
        CartItem cartItem = findOrderFromProduct(product);
        if (cartItem == null) {
            return;
        }
        items.remove(cartItem);
        recalculate();
    }

    private void recalculate() {
        totalCost = 0.0;
        for (CartItem o : items) {
            o.setTotalPrice(o.getQuantity() * o.getProduct().getPrice());
            totalCost += o.getTotalPrice();
        }
    }

    private CartItem findOrderFromProduct(ProductDTO product) {
        return items.stream().filter(o -> o.getProduct().getId().equals(product.getId())).findFirst().orElse(null);
    }
}
