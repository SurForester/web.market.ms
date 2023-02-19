package org.spring.web.market.converters;

import org.spring.web.market.api.dto.ProductDTO;
import org.spring.web.market.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOConverter {
    public Product modelProductDTOToItem(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setPrice(productDTO.getPrice());
        product.setTitle(product.getTitle());
        return product;
    }

    public ProductDTO modelProductItemToDTO(Product product) {
        return ProductDTO.newBuilder()
                .withId(product.getId())
                .withPrice(product.getPrice())
                .withTitle(product.getTitle())
                .withPath(product.getImages().get(0).getPath())
                .build();
    }

}
