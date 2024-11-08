package store.domain.cart;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.domain.product.Product;
import store.domain.promotion.Promotion;

import java.util.Optional;
import java.util.stream.Stream;

public class OrderProductTest {

    @ParameterizedTest
    @MethodSource("provideData")
    void 프로모션_혜택_없이_결제해야_하는_상품_수량_계산_기능_테스트(){
        Product product = Product.of("상품", 3000, 5, null);
        Promotion promotion = Promotion.of("프로모션", 2, 1, null, null);
        OrderProduct orderProduct = OrderProduct.of(product, 10, Optional.of(promotion));



    }

    static Stream<Arguments> provideData() {
        //buy, get,
        return Stream.of(
                Arguments.of(2, 1, 2, 1),
                Arguments.of(2, 1, 1, 0),
                Arguments.of(2, 1, 3, 0),
                Arguments.of(2, 1, 4, 0),
                Arguments.of(2, 1, 5, 1),
                Arguments.of(5, 2, 5, 2),
                Arguments.of(5, 2, 7, 0),
                Arguments.of(5, 2, 10, 0),
                Arguments.of(5, 2, 12, 2),
                Arguments.of(5, 2, 13, 1)
        );
    }
}
