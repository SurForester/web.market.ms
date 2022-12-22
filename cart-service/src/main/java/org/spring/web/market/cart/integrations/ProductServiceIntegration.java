package org.spring.web.market.cart.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.spring.web.market.api.dto.ProductDTO;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final RestTemplate restTemplate;

    public Optional<ProductDTO> getProductById(Long id) {
        return Optional.ofNullable(restTemplate.getForObject("http://localhost:8189/market/products/" + id, ProductDTO.class));
    }

}
