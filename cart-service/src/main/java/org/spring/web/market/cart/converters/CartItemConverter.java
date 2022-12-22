package org.spring.web.market.cart.converters;

import org.spring.web.market.api.dto.CartItemDTO;
import org.spring.web.market.cart.entities.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemConverter {
    public CartItemDTO modelCartItemToDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setItemPrice(cartItem.getItemPrice());
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setProduct(cartItem.getProduct());
        cartItemDTO.setItemPrice(cartItem.getItemPrice());
        cartItemDTO.setTotalPrice(cartItem.getTotalPrice());
        return cartItemDTO;
    }
}
