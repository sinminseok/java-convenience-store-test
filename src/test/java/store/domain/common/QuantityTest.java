package store.domain.common;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class QuantityTest {

    @ParameterizedTest
    @CsvSource(value = {"9, true", "10, true", "11, false"})
    void 수량이_충분한지_확인하는_테스트(int value, boolean expectResult){
        //given
        Quantity quantity = Quantity.from(10);

        //when
        boolean result = quantity.isEnough(value);

        //then
        assertThat(result).isEqualTo(expectResult);
    }


}
