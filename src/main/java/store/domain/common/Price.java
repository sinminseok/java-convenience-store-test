package store.domain.common;

public class Price {
    private final int value;

    private Price(int value) {
        this.value = value;
    }

    public static Price from(int value){
        //todo 검증
        return new Price(value);
    }

    public boolean isEnough(int price){
        return value - price >= 0;
    }

    public int getValue() {
        return value;
    }
}
