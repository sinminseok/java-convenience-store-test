package store.domain.common;

public class Name {
    private final String value;

    public Name(String value) {
        this.value = value;
    }

    public static Name from(String value){
        //todo 검증
        return new Name(value);
    }

    //todo equals 로 변경

    public boolean isSameName(String name){
        return value.equals(name);
    }

    public String getValue() {
        return value;
    }
}