package org.spring.web.market.cart.entities;

import lombok.Data;
import org.spring.web.market.api.dto.ProductDTO;

@Data
public class CartItem {
    private Long id;
    private Long quantity;
    private Double itemPrice;
    private Double totalPrice;
    private ProductDTO product;
}
