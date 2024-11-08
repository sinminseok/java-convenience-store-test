package store.domain.cart;

import store.domain.common.Name;
import store.domain.common.Quantity;
import store.domain.product.Product;
import store.domain.promotion.Promotion;

import java.util.Optional;

public class OrderProduct {
    private Product product;
    private final Quantity quantity;
    private final Optional<Promotion> promotion;

    private OrderProduct(Product product, Quantity quantity, Optional<Promotion> promotion) {
        this.product = product;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static OrderProduct of(Product product, int quantity, Optional<Promotion> promotion) {

        return new OrderProduct(product, Quantity.from(quantity), promotion);
    }

    public int calculateOrderPrice() {
        int price = product.getPriceValue();
        return quantity.calculatePrice(price);
    }

    public int calculatePartialNotPromotionPrice() {

        return promotion.get().countProductWithoutPromotion(quantity.getValue()) * product.getPriceValue();

    }


    public int calculatePromotionDiscount() {
        int freeProductCount = product.findGiveawayCount(quantity.getValue());
        return freeProductCount * product.getPriceValue();
    }

    public boolean isPromotionOrder() {
        return promotion.isPresent();
    }

    public boolean isApplyPromotion() {
        if (promotion.isPresent() && product.isEnoughPromotionQuantity(quantity.getValue())) {
            return true;
        }
        return false;
    }

    public FreeItem findGiveAway() {
        Name productName = product.getName();
        int freeProductCount = product.findGiveawayCount(quantity.getValue());
        return FreeItem.of(productName, freeProductCount);
    }

    public int getQuantityValue() {
        return quantity.getValue();
    }

    public Optional<Promotion> getPromotion() {
        return promotion;
    }

    public String getProductName() {
        return product.getNameValue();
    }
}
