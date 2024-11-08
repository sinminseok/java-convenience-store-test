package store.domain.product;

import store.domain.common.Name;
import store.domain.common.Price;
import store.domain.common.Quantity;
import store.domain.promotion.Promotion;

import java.time.LocalDate;
import java.util.Optional;

public class Product {
    private final Name name;
    private final Price price;
    private final Quantity quantity;
    private Optional<Promotion> promotion;


    private Product(Name name, Price price, Quantity quantity, Optional<Promotion> promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Product of(String name, int price, int quantity, Optional<Promotion> promotion) {
        return new Product(Name.from(name), Price.from(price), Quantity.from(quantity), promotion);
    }

    public void decreaseQuantity(int value) {
        this.quantity.decreaseValue(value);
    }

    public int findGiveawayCount(int userQuantity) {
        return promotion.get().countFreeItem(userQuantity);
    }

    public boolean canMoreGetFreeProduct(int quantity) {
        return promotion.get().isMoreGetProduct(quantity);
    }

    public boolean isEnoughPromotionQuantity(int quantity) {
        return promotion.get().isEnoughApplyPromotion(this.quantity.getValue(), quantity);
    }

    // 프로모션 재고가 부족해서, 일부 수량을 프로모션 혜택없이 결제해야하는 수량 계산 기능
    public int calculateNotPromotionRemainQuantity(int quantity) {
        int count = this.quantity.getValue() -promotion.get().countProductWithoutPromotion(this.quantity.getValue());
        return quantity - count;
    }

    public int countMoreFreeItems(int quantity) {
        return promotion.get().countMoreFreeItems(quantity);
    }

    public boolean isPromotionProduct() {
        return promotion.isPresent();
    }

    public boolean isPromotionPeriod(LocalDate localDate) {
        if (promotion.get().isPromotionPeriod(localDate)) {
            return true;
        }
        return false;
    }

    public boolean isSameName(String name) {
        return this.name.isSameName(name);
    }

    public String getNameValue() {
        return name.getValue();
    }

    public Name getName() {
        return name;
    }

    public int getPriceValue() {
        return price.getValue();
    }

    public int getQuantityValue() {
        return quantity.getValue();
    }

    public Optional<Promotion> getPromotion() {
        return promotion;
    }
}
