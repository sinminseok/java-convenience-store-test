package store.domain.cart;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    private List<OrderProduct> products;

    public ShoppingCart(List<OrderProduct> products) {
//        System.out.println("장바구니 정보");
//        for(OrderProduct product : products){
//            System.out.println("상품 이름 : " + product.getProductName());
//            System.out.println("상품 갯수 : " + product.getQuantityValue());
//            System.out.println("프로모션 여부 : " + (product.getPromotion().isPresent()?"존재":"존재하지 않음"));
//        }
        this.products = products;
    }

    /**
     * 모든 주문 내역 정보를 조회하는 메서드
     */
    public List<OrderProduct> getProducts() {
        return products;
    }

    /**
     * 증정품 정보를 조회하는 메서드
     */
    public List<FreeItem> getGiveaways() {
        Map<String, FreeItem> giveawayMap = new HashMap<>();

        products.stream()
                .filter(OrderProduct::isApplyPromotion) // 프로모션이 적용될 수 있는 경우만 필터링
                .map(OrderProduct::findGiveAway)       // 각 OrderProduct에서 FreeItem 생성
                .forEach(giveaway -> processGiveaway(giveawayMap, giveaway));

        return new ArrayList<>(giveawayMap.values());
    }

    private void processGiveaway(Map<String, FreeItem> giveawayMap, FreeItem giveaway) {
        String name = giveaway.getNameValue();
        giveawayMap.merge(name, giveaway, (existing, newGiveaway) ->
                FreeItem.of(existing.getName(),
                        existing.getQuantityValue() + newGiveaway.getQuantityValue()));
    }

    /**
     * 총 주문 수량을 조회하는 메서드
     */
    public int getTotalOrderCount(){
        return products.stream()
                .mapToInt(OrderProduct::getQuantityValue)
                .sum();
    }

    /**
     * 총 주문 금액 계산 메서드
     */
    public int calculateOrderPrice(){
        return products.stream()
                .mapToInt(orderProduct -> orderProduct.calculateOrderPrice())
                .sum();
    }

    /**
     * 프로모션(행사) 할인 금액 계산 메서드
     */
    public int calculateDiscountPromotionPrice(){
        return products.stream()
                .filter(OrderProduct::isPromotionOrder)
                .mapToInt(orderProduct -> orderProduct.calculatePromotionDiscount())
                .sum();
    }

    /**
     * 멤버십 할인 금액 계산 메서드
     */
    public int calculateDiscountMemberShipPrice(boolean isMemberShip){
        //todo refactor
        if(!isMemberShip){
            return 0;
        }
        //todo fix

        //멤버십이 일부만 적용된 경우 -> o ex)2+1 이벤트인데 5개를 구매하면 2개는 멤버십 적용이 안된거다.
        int pattialPromotionPrice = products.stream()
                .filter(orderProduct -> orderProduct.isPromotionOrder())
                .mapToInt(OrderProduct::calculatePartialNotPromotionPrice)
                .sum();
        //멤버십이 전부 적용된 경우 -> x
        //멤버십이 없는 경우 -> o
        int notPromotionPrice = products.stream()
                .filter(orderProduct -> !orderProduct.isPromotionOrder())
                .mapToInt(OrderProduct::calculateOrderPrice)
                .sum();

        int sumPrice = pattialPromotionPrice + notPromotionPrice;
        //프로모션 미 적용금액의 30 % 최대 8000원
        double discountPrice = (sumPrice * 0.3);
        if(discountPrice >= 8000){
            return 8000;
        }
        return (int) discountPrice;
    }

    /**
     * 지불 금액 계산 메서드
     */
    public int calculatePaymentPrice(int promotionDiscount, int memberShipDiscount){
        return calculateOrderPrice() - promotionDiscount - memberShipDiscount;
    }

}
