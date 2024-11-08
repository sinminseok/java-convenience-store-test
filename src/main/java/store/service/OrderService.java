package store.service;

import store.domain.cart.ShoppingCart;
import store.dto.GiveawayResponse;
import store.dto.OrderProductResponse;
import store.dto.ReceiptResponse;
import store.mapper.OrderMapper;

import java.util.List;

public class OrderService {

    public ReceiptResponse generateReceipt(ShoppingCart shoppingCart, boolean isMemberShip) {
        List<OrderProductResponse> orderProductResponses = OrderMapper.toOrderProductResponses(shoppingCart.getProducts());
        List<GiveawayResponse> giveawayResponses = OrderMapper.toGiveawayResponse(shoppingCart.getGiveaways());

        int totalOrderPrice = shoppingCart.calculateOrderPrice();
        int promotionDiscountPrice = shoppingCart.calculateDiscountPromotionPrice();
        int memberShipDiscountPrice = shoppingCart.calculateDiscountMemberShipPrice(isMemberShip);
        int paymentPrice = shoppingCart.calculatePaymentPrice(promotionDiscountPrice, memberShipDiscountPrice);
        int totalCount = shoppingCart.getTotalOrderCount();

        return OrderMapper.toReceiptResponse(orderProductResponses, giveawayResponses, totalCount, totalOrderPrice, promotionDiscountPrice, memberShipDiscountPrice, paymentPrice);
    }

}
