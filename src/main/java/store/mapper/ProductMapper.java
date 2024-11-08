package store.mapper;


import store.domain.product.Product;
import store.dto.ProductResponse;

import java.util.List;

public class ProductMapper {

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getNameValue(),
                product.getPriceValue(),
                product.getQuantityValue(),
                product.getPromotion().map(promotion -> promotion.getName().getValue()).orElse("") // Optional handling for promotion
        );
    }
}
