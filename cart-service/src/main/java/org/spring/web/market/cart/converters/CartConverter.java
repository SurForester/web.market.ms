package org.spring.web.market.cart.converters;

import lombok.RequiredArgsConstructor;
import org.spring.web.market.api.dto.CartDTO;
import org.spring.web.market.cart.entities.Cart;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemConverter cartItemConverter;

    public CartDTO modelCartToDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setTotalCost(cart.getTotalCost());
        cartDTO.setItems(cart.getItems().stream()
                .map(cartItemConverter::modelCartItemToDTO)
                .collect(Collectors.toList()));
        return cartDTO;
    }
}
