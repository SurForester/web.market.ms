package org.spring.web.market.controllers;

import org.spring.web.market.api.dto.ProductDTO;
import org.spring.web.market.converters.ProductDTOConverter;
import org.spring.web.market.entities.Product;
import org.spring.web.market.services.CategoryService;
import org.spring.web.market.services.ImageSaverService;
import org.spring.web.market.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;
    private ImageSaverService imageSaverService;
    private ProductDTOConverter productDTOConverter;
    private Map<Long, Product> productCache;

    public ProductController() {
        this.productCache = new HashMap<>();
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setImageSaverService(ImageSaverService imageSaverService) {
        this.imageSaverService = imageSaverService;
    }

    @Autowired
    public void setProductDTOConverter(ProductDTOConverter productDTOConverter) {
        this.productDTOConverter = productDTOConverter;
    }

    @GetMapping("/{id}")
    public ProductDTO getProductDTO(@PathVariable("id") Long id) {
        return productDTOConverter.modelProductItemToDTO(getProduct(id));
    }

    private Product getProduct(Long id) {
        Product result;
        if (productCache.containsKey(id)) {
            result = productCache.get(id);
        } else {
            result = productService.getProductById(id);
            productCache.put(id, result);
        }
        return result;
    }

}
