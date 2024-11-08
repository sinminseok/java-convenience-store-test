package store.domain.promotion;

public class PromotionQuantity {
    private final int buy;
    private final int get;

    private PromotionQuantity(int buy, int get) {
        this.buy = buy;
        this.get = get;
    }

    public static PromotionQuantity of(int buy, int get) {
        //todo validate
        return new PromotionQuantity(buy, get);
    }

    // 무료로 더 받을 수 있는지 확인
    public boolean canReceiveAdditionalFreeItems(int quantity) {
        if (quantity < buy) {
            return false;
        }
        int sum = buy + get;
        int remain = quantity % sum;

        if (quantity == buy) {
            return true;
        }
        if (remain >= buy && remain < sum) {
            return true;
        }
        return false;
    }

    //ex) 2+1 이벤트인데 5개를 사면? 프로모션이 적용되지 않은 2개가 반환된다.
    public int countWithoutPromotion(int quantity) {
        if (quantity <= buy) {
            return quantity;
        }
        int sum = buy + get;
        if (sum == quantity) {
            return 0;
        }
        return quantity % sum;
    }

    //더 받을 수 있는 증정 상품 갯수를 구하는 기능
    public int countMoreFreeItems(int quantity) {

        if (quantity < buy) {
            return 0;
        }
        int sum = buy + get;
        int remain = quantity % sum;
        if (remain >= buy) {
            return sum - remain;
        }
        return 0;
    }

    //프로모션 혜택을 받지 못하는 수량 계산 기능
    public int countUnPromotedItems(int quantity) {


        int sum = buy + get;
        //프로모션 혜택을 전부 못 받는다. 2+1 이벤트인데 2개가 최대이면 결국 할인받는건 0개이기때문!
        if (quantity == buy) {
            return quantity;
        }
        if (quantity < sum) {
            return 0;
        }
        return quantity % sum;
    }

    // 증정품 갯수 구하는 메서드
    public int countFreeItem(int quantity) {
        if (quantity <= buy) {
            return 0;
        }
        int sum = buy + get;
        return (quantity / sum) * get;
    }

    // /프로모션 재고가 부족한지 판별하는 메서드
    public boolean isEnoughApplyPromotion(int remainQuantity, int purchaseQuantity) {
        int sum = buy + get;
        if(remainQuantity < sum) {
            return false;
        }
        return remainQuantity >= purchaseQuantity;
    }



    public int getGet() {
        return get;
    }
}
