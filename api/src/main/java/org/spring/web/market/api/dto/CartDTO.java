package org.spring.web.market.api.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDTO {
    private List<CartItemDTO> items;
    private BigDecimal totalCost;

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
