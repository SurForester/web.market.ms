package org.spring.web.market.integrations;

import lombok.RequiredArgsConstructor;
import org.spring.web.market.api.dto.CartDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final RestTemplate restTemplate;

    public CartDTO getCart(String sessionId) {
        return restTemplate.getForObject("http://localhost:8190/cart/get/" + sessionId, CartDTO.class);
    }
}
