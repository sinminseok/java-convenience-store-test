package store.mapper;

import store.domain.cart.FreeItem;
import store.domain.cart.OrderProduct;
import store.dto.GiveawayResponse;
import store.dto.OrderProductResponse;
import store.dto.ReceiptResponse;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

//    public static List<OrderProductResponse> toOrderProductResponses(List<OrderProduct> products) {
//        return products.stream()
//                .map(orderProduct -> {
//                    return new OrderProductResponse(
//                            orderProduct.getProductName(),
//                            orderProduct.getQuantityValue(),
//                            orderProduct.calculateOrderPrice()
//                    );
//                }).collect(Collectors.toList());
//    }

    public static List<OrderProductResponse> toOrderProductResponses(List<OrderProduct> products) {
        return products.stream()
                .collect(Collectors.groupingBy(OrderProduct::getProductName))
                .entrySet().stream()
                .map(entry -> new OrderProductResponse(
                        entry.getKey(),
                        entry.getValue().stream().mapToInt(OrderProduct::getQuantityValue).sum(),
                        entry.getValue().stream().mapToInt(OrderProduct::calculateOrderPrice).sum()
                ))
                .toList();
    }

    public static List<GiveawayResponse> toGiveawayResponse(List<FreeItem> giveaways) {
        return giveaways.stream()
                .map(giveaway -> {
                    return new GiveawayResponse(
                            giveaway.getNameValue(),
                            giveaway.getQuantityValue()
                    );
                }).collect(Collectors.toList());
    }

    public static ReceiptResponse toReceiptResponse(List<OrderProductResponse> orderProductResponses, List<GiveawayResponse> giveawayResponses, int totalCount,int totalOrderPrice, int promotionDiscountPrice, int memberShipDiscountPrice, int paymentPrice) {
        return new ReceiptResponse(orderProductResponses, giveawayResponses, totalCount,totalOrderPrice, promotionDiscountPrice,memberShipDiscountPrice,paymentPrice);
    }
}
