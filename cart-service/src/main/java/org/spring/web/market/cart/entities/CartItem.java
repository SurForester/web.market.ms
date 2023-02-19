package org.spring.web.market.cart.entities;

import lombok.Data;
import org.spring.web.market.api.dto.ProductDTO;
import java.math.BigDecimal;

@Data
public class CartItem {
    private Long id;
    private Long quantity;
    private BigDecimal itemPrice;
    private BigDecimal totalPrice;
    private ProductDTO product;
}
