package store.domain.common;

public class Quantity {
    private int value;

    public Quantity(int value) {
        this.value = value;
    }

    public static Quantity from(int value) {
        //todo 검증
        return new Quantity(value);
    }

    public boolean isEnough(int quantity) {
        return this.value >= quantity;
    }

    public int calculatePrice(int price){
        return price * value;
    }

    public void decreaseValue(int value) {
        this.value -= value;
    }

    public int getValue() {
        return value;
    }
}