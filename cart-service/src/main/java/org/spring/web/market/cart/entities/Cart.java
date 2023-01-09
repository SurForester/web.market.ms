package org.spring.web.market.cart.entities;

import lombok.Data;
import org.spring.web.market.api.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private Long quantity;
    private BigDecimal totalCost;

    public Cart() {
        items = new ArrayList<>();
        totalCost = BigDecimal.ZERO;
    }

    public void add(ProductDTO product) {
        CartItem cartItem = findOrderFromProduct(product);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setItemPrice(product.getPrice());
            cartItem.setQuantity(0L);
            cartItem.setId(0L);
            cartItem.setTotalPrice(BigDecimal.ZERO);
            items.add(cartItem);
        }
        cartItem.setQuantity(cartItem.getQuantity() + 1);
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
        totalCost = BigDecimal.ZERO;
        quantity = 0L;
        for (CartItem o : items) {
            o.setTotalPrice(new BigDecimal(o.getQuantity()).multiply(o.getProduct().getPrice()));
            totalCost = totalCost.add(o.getTotalPrice());
            quantity++;
        }
    }

    private CartItem findOrderFromProduct(ProductDTO product) {
        return items.stream().filter(o -> o.getProduct().getId().equals(product.getId())).findFirst().orElse(null);
    }

    public void changeQuantity(ProductDTO product, Integer delta) {
        for (CartItem item : items) {
            if (product.getId().equals(item.getId())) {
                item.setQuantity(item.getQuantity() + delta);
                if (item.getQuantity() == 0) {
                    remove(item.getProduct());
                    return;
                }
                recalculate();
                return;
            }
        }
    }

}
