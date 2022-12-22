package org.spring.web.market.api.dto;

import java.util.List;

public class CartDTO {
    private List<CartItemDTO> items;
    private Double totalCost;

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}
