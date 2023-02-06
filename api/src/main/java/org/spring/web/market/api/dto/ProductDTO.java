package org.spring.web.market.api.dto;

import java.math.BigDecimal;

public class ProductDTO {

    private final Long id;
    private final String title;
    private final BigDecimal price;
    private final String categoryTitle;
    private final String path;

    private ProductDTO(Builder builder) {
        id = builder.id;
        title = builder.title;
        price = builder.price;
        categoryTitle = builder.categoryTitle;
        path = builder.path;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(ProductDTO copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.title = copy.getTitle();
        builder.price = copy.getPrice();
        builder.categoryTitle = copy.getCategoryTitle();
        builder.path = copy.getPath();
        return builder;
    }

    public static final class Builder {
        private Long id;
        private String title;
        private BigDecimal price;
        private String categoryTitle;
        private String path;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(Long val) {
            id = val;
            return this;
        }

        public Builder withTitle(String val) {
            title = val;
            return this;
        }

        public Builder withPrice(BigDecimal val) {
            price = val;
            return this;
        }

        public Builder withCategoryTitle(String val) {
            categoryTitle = val;
            return this;
        }

        public Builder withPath(String val) {
            path = val;
            return this;
        }

        public ProductDTO build() {
            return new ProductDTO(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public String getPath() {
        return path;
    }
}
