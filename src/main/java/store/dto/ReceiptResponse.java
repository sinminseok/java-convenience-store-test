package store.dto;

import java.util.List;

public record ReceiptResponse(
        List<OrderProductResponse> orderProductResponses,
        List<GiveawayResponse> giveawayResponses,
        int totalCount,
        int totalPrice,
        int promotionDiscount,
        int memberShipDiscount,
        int payment
) {
}