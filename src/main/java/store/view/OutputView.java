package store.view;

import store.dto.GiveawayResponse;
import store.dto.OrderProductResponse;
import store.dto.ProductResponse;
import store.dto.ReceiptResponse;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OutputView {
    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.";
    private static final String STORE_HEADER = String.format("%s%s%s", "==============", "W 편의점", "==============");
    private static final String PROMOTION_HEADER = String.format("%s%s%5s%s", "=============", "증", "", "정===============");
    private static final String PRODUCT_INFO_FORMAT = "- %s %s원 %s %s%n"; // 가격에 쉼표 적용
    private static final String GIVEAWAY_INFO_FORM = "%-17s %2d";
    private static final String OUT_OF_STOCK_MESSAGE = "재고 없음";
    private static final String PRODUCT_INFO_HEADER = String.format("%-15s %5s %10s", "상품명", "수량", "금액");
    private static final String SEPARATOR_LINE = "====================================";
    private static final String TOTAL_PRICE_FORMAT = "총구매액%15s %13s%n";
    private static final String PRICE_FORMAT = "%-20s %12s%n";
    private static final String PRODUCT_INFO_FORM = "%-19s %-4d %10s%n";

    // 숫자 포맷터 (한국 로케일 적용)
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getNumberInstance(Locale.KOREA);

    public static void printProductInformation(List<ProductResponse> productResponses) {
        System.out.println(WELCOME_MESSAGE);
        System.out.println();

        for (ProductResponse product : productResponses) {
            String promotion = (product.promotion() != null && !product.promotion().isEmpty()) ? product.promotion() : "";
            String stockStatus = product.quantity() > 0 ? product.quantity() + "개" : OUT_OF_STOCK_MESSAGE;

            String formattedPrice = CURRENCY_FORMAT.format(product.price());
            System.out.printf(PRODUCT_INFO_FORMAT, product.name(), formattedPrice, stockStatus, promotion);
        }
    }

    public static void printReceipt(ReceiptResponse receiptResponse) {
        System.out.println(STORE_HEADER);
        System.out.println(PRODUCT_INFO_HEADER);
        for (OrderProductResponse orderProduct : receiptResponse.orderProductResponses()) {
            String formattedPrice = CURRENCY_FORMAT.format(orderProduct.price());
            System.out.printf(PRODUCT_INFO_FORM, orderProduct.name(), orderProduct.quantity(), formattedPrice);
        }

        System.out.println(PROMOTION_HEADER);
        for (GiveawayResponse giveaway : receiptResponse.giveawayResponses()) {
            System.out.println(String.format(GIVEAWAY_INFO_FORM, giveaway.name(), giveaway.quantity()));

        }

        System.out.println(SEPARATOR_LINE);
        System.out.printf(TOTAL_PRICE_FORMAT, CURRENCY_FORMAT.format(receiptResponse.totalCount()), CURRENCY_FORMAT.format(receiptResponse.totalPrice()));
        System.out.printf(PRICE_FORMAT, "행사할인", CURRENCY_FORMAT.format(-receiptResponse.promotionDiscount()));
        System.out.printf(PRICE_FORMAT, "멤버할인", CURRENCY_FORMAT.format(-receiptResponse.memberShipDiscount()));
        System.out.printf(PRICE_FORMAT, "내실돈 ", CURRENCY_FORMAT.format(receiptResponse.payment()));
    }

}
