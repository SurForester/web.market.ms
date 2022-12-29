package org.spring.web.market.integrations;

import lombok.RequiredArgsConstructor;
import org.spring.web.market.api.dto.CartDTO;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final RestTemplate restTemplate;

    public CartDTO getCart(HttpSession session) {
        return restTemplate.getForObject("http://localhost:8090/cart",
                CartDTO.class, session);
    }

    public void addProductToCart(HttpSession session, Long id) {
        restTemplate.put("http://localhost:8090/cart/add/" + id, session);
    }

    public void clearCart(HttpSession session) {
        restTemplate.put("http://localhost:8090/cart/clear", session);
    }

    public void removeProductFrmCart(HttpSession session, Long id) {
        restTemplate.put("http://localhost:8090/cart/remove/" + id, session);
    }

}
