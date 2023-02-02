package org.spring.web.market.cart.integrations;

import lombok.RequiredArgsConstructor;
import org.spring.web.market.api.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.spring.web.market.api.dto.ProductDTO;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final WebClient productServiceIntegrationProperties;

    public ProductDTO getProductById(Long id) {
        return productServiceIntegrationProperties.get()
                .uri("products/" + id)
                .retrieve()
                .onStatus(HttpStatus -> HttpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Товар не найден в МС."))
                )
                .bodyToMono(ProductDTO.class)
                .block();
    }

}
