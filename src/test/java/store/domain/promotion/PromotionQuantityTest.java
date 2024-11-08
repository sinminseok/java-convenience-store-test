package store.domain.promotion;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PromotionQuantityTest {

    @ParameterizedTest
    @MethodSource("provideCanReceiveAdditionalFreeItemsTestCases")
    void 프로모션_이벤트에서_증정품을_더_받을_수_있는지_확인하는_기능_테스트(int buy, int get, int quantity, boolean expectedResult){
        PromotionQuantity promotionQuantity = PromotionQuantity.of(buy, get);

        boolean result = promotionQuantity.canReceiveAdditionalFreeItems(quantity);

        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("provideRemainingForNextFreeItemTestCases")
    void 프로모션_이벤트로_더_받을_수_있는_상품_갯수를_계산하는_기능_테스트(int buy, int get, int quantity, int expectedResult){
        PromotionQuantity promotionQuantity = PromotionQuantity.of(buy, get);

        int result = promotionQuantity.countMoreFreeItems(quantity);

        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("provideRemainingAfterPromotionTestCases")
    void 프로모션_혜택을_받지_못하는_수량_계산_기능_테스트(int buy, int get, int quantity, int expectedResult){
        PromotionQuantity promotionQuantity = PromotionQuantity.of(buy, get);

        int result = promotionQuantity.countUnPromotedItems(quantity);

        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("providePartialNotPromotionCountTestCases")
    void 프로모션_혜택이_적용되지_않은_나머지_상품_수량_계산_기능_테스트(int buy, int get, int quantity, int expectedResult){
        PromotionQuantity promotionQuantity = PromotionQuantity.of(buy, get);

        int result = promotionQuantity.countWithoutPromotion(quantity);

        assertThat(result).isEqualTo(expectedResult);
    }

    static Stream<Arguments> providePartialNotPromotionCountTestCases() {
        //buy, get, quantity, expectedResult
        return Stream.of(
                Arguments.of(2, 1, 2, 2),
                Arguments.of(2, 1, 3, 0),
                Arguments.of(2, 1, 4, 1),
                Arguments.of(2, 1, 5, 2),
                Arguments.of(2, 1, 6, 0)
        );
    }

    static Stream<Arguments> provideRemainingForNextFreeItemTestCases() {
        //buy, get, quantity, expectedResult
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

    static Stream<Arguments> provideCanReceiveAdditionalFreeItemsTestCases() {
        //buy, get, quantity, expectedResult
        return Stream.of(
                Arguments.of(2, 1, 2, true),
                Arguments.of(2, 1, 1, false),
                Arguments.of(2, 1, 3, false),
                Arguments.of(2, 1, 4, false),
                Arguments.of(2, 1, 5, true),
                Arguments.of(5, 2, 5, true),
                Arguments.of(5, 2, 6, true),
                Arguments.of(5, 2, 7, false),
                Arguments.of(5, 3, 8, false),
                Arguments.of(5, 3, 9, false),
                Arguments.of(5, 3, 21, true)
        );
    }

    static Stream<Arguments> provideRemainingAfterPromotionTestCases() {
        //buy, get, quantity, expectedResult
        return Stream.of(
                Arguments.of(2, 1, 2, 2),
                Arguments.of(2, 1, 3, 0),
                Arguments.of(2, 1, 4, 1),
                Arguments.of(2, 1, 5, 2),
                Arguments.of(2, 1, 6, 0),
                Arguments.of(2, 1, 7, 1),
                Arguments.of(5, 2, 6, 0),
                Arguments.of(5, 2, 7, 0),
                Arguments.of(5, 2, 8, 1),
                Arguments.of(5, 2, 12, 5)

        );
    }


}
