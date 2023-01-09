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
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setTitle(product.getTitle());
        productDTO.setPath(product.getImages().get(0).getPath());
        productDTO.setCategoryTitle(productDTO.getCategoryTitle());
        return productDTO;
    }

}
