package store.domain.promotion;


import store.domain.common.Name;

import java.time.LocalDate;


public class Promotion {
    private final Name name;
    private final PromotionQuantity promotionQuantity;
    private final PromotionPeriod promotionPeriod;

    private Promotion(Name name, PromotionQuantity promotionQuantity, PromotionPeriod promotionPeriod) {
        this.name = name;
        this.promotionQuantity = promotionQuantity;
        this.promotionPeriod = promotionPeriod;
    }

    public static Promotion of(String name, int buy, int get, String startDate, String endDate ) {
        Name promotionName = Name.from(name);
        PromotionQuantity promotionQuantity = PromotionQuantity.of(buy, get);
        PromotionPeriod promotionPeriod = PromotionPeriod.of(startDate, endDate);
        return new Promotion(promotionName, promotionQuantity, promotionPeriod);
    }

    public boolean isPromotionPeriod(LocalDate localDate){
        return promotionPeriod.isDateInPeriod(localDate);
    }

    public int countMoreFreeItems(int quantity){
        return promotionQuantity.countMoreFreeItems(quantity);
    }

    public int countFreeItem(int quantity){
        return promotionQuantity.countFreeItem(quantity);
    }

    public boolean isMoreGetProduct(int quantity){
        return promotionQuantity.canReceiveAdditionalFreeItems(quantity);
    }

    public boolean isEnoughApplyPromotion(int remainQuantity, int purchaseQuantity){
        return promotionQuantity.isEnoughApplyPromotion(remainQuantity, purchaseQuantity);
    }

    //프로모션이 적용되지 않은 일부 상품 갯수를 계산하는 메서드
    //ex) 2+1 이벤트인데 5개를 사면 2를 반환,
    public int countProductWithoutPromotion(int quantity){
        return promotionQuantity.countWithoutPromotion(quantity);
    }

    public boolean isSameName(String name){
        return this.name.isSameName(name);
    }

    public Name getName() {
        return name;
    }

}
