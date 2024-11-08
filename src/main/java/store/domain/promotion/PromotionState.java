package store.domain.promotion;

public enum PromotionState {
    APPLIED, // 프로모션이 적용 확정된 경우
    NOT_APPLIED, // 프로모션 적용 불가 확정된 경우
    ELIGIBLE, // 프로모션 적용 가능하나 고객이 해당 수량을 가져오지 않은 경우
    PARTIAL_APPLIED; // 프로모션 재고 부족으로 일부 수량만 혜택 없이 결제해야 하는 경우

    public boolean isApplyPromotion(){
        return !this.equals(NOT_APPLIED);
    }
}
