package store.domain.cart;

import store.domain.common.Name;
import store.domain.common.Quantity;

public class FreeItem {
    private final Name name;
    private final Quantity quantity;

    private FreeItem(Name name, Quantity quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static FreeItem of(Name name, int quantity){
        return new FreeItem(name, Quantity.from(quantity));
    }

    public String getNameValue() {
        return name.getValue();
    }

    public Name getName() {
        return name;
    }

    public int getQuantityValue() {
        return quantity.getValue();
    }
}
