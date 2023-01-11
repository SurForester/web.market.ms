package org.spring.web.market.integrations;

import lombok.RequiredArgsConstructor;
import org.spring.web.market.api.dto.CartDTO;
import org.spring.web.market.api.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {

    private final WebClient cartServiceWebClient;

    public CartDTO getCart(String user) {
        return cartServiceWebClient.get()
                .uri("cart/")
                .attribute("user", user)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Корзина не найдена в продуктовом MS"))
                )
                .bodyToMono(CartDTO.class)
                .block();
    }

    public void addToCart(Long productId, String user) {
        cartServiceWebClient.get()
                .uri("cart/add/" + productId)
                .attribute("user", user)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Корзина не найдена в продуктовом MS"))
                )
                .toBodilessEntity()
                .block();
    }

    public void clearCart(String user) {
        cartServiceWebClient.get()
                .uri("cart/clear")
                .attribute("user", user)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Корзина не найдена в продуктовом MS"))
                )
                .toBodilessEntity()
                .block();
    }
}
