package store.controller;

import store.domain.cart.OrderProduct;
import store.domain.cart.ShoppingCart;
import store.domain.promotion.PromotionState;
import store.dto.ProductResponse;
import store.dto.ReceiptResponse;
import store.dto.ProductRequest;
import store.service.OrderService;
import store.service.ProductService;
import store.view.Answer;
import store.view.InputView;
import store.view.OutputView;

import java.util.ArrayList;
import java.util.List;

//todo fix
public class ConvenienceController {

    private final ProductService productService;
    private final OrderService orderService;

    public ConvenienceController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    //[감자칩-6]
    public void run() {
        while (true) {
            showProductInformation();
            List<ProductRequest> productRequests = inputPurchaseProducts();
            //todo fix
            List<OrderProduct> orderProducts = checkPromotion(productRequests);
            ShoppingCart shoppingCart = new ShoppingCart(orderProducts);
            boolean isMemberShip = checkMemberShip();
            generateReceipt(shoppingCart, isMemberShip);
            buyProducts(shoppingCart);
            if (!shouldRetry()) {
                break;
            }
        }
    }


    private List<OrderProduct> checkPromotion(List<ProductRequest> productRequests) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (ProductRequest productRequest : productRequests) {
            PromotionState promotionState = productService.checkPromotionState(productRequest);
            if (promotionState.isApplyPromotion()) {
                List<OrderProduct> orderProduct = checkPromotionQuestion(productRequest, promotionState);
                orderProducts.addAll(orderProduct);
                continue;
            }
            orderProducts.add(productService.generalOrder(productRequest));
        }
        return orderProducts;
    }

    private List<OrderProduct> checkPromotionQuestion(ProductRequest productRequest, PromotionState promotionState) {
        // 프로모션 적용 가능하나 고객이 해당 수량을 가져오지 않은 경우
        if (promotionState.equals(PromotionState.ELIGIBLE)) {
            int count = productService.findFreePromotionProduct(productRequest);
            Answer answer = InputView.readPromotionOption(productRequest.name(), count);
            if (answer.isYes()) {
                return List.of(productService.addFreePromotionProduct(productRequest));
            }
            return List.of(productService.skipFreePromotionProduct(productRequest));
        }
        // 프로모션 재고 부족으로 일부 수량만 혜택 없이 결제해야 하는 경우
        if (promotionState.equals(PromotionState.PARTIAL_APPLIED)) {
            int count = productService.findNotApplyPromotionCount(productRequest);
            Answer answer = InputView.readNonPromotionPurchaseOption(productRequest.name(), count);
            if (answer.isYes()) {
                //일부 수량에 대해 정가로 결제한다:
                return productService.chargePartialQuantityAtFullPrice(productRequest);
            }
            //정가로 결제해야 하는 수량만큼 제외한 후 결제를 진행한다: 프로모션
            return List.of(productService.proceedWithReducedQuantityPayment(productRequest, count));

        }
        return List.of(productService.generalPromotionOrder(productRequest));
    }



    private List<ProductRequest> inputPurchaseProducts() {
        return InputView.readItem();
    }

    private void showProductInformation() {
        List<ProductResponse> all = productService.findAll();
        OutputView.printProductInformation(all);
    }

    private void generateReceipt(ShoppingCart shoppingCart, boolean isMemberShip) {
        ReceiptResponse receiptResponse = orderService.generateReceipt(shoppingCart, isMemberShip);

        OutputView.printReceipt(receiptResponse);
    }

    private void buyProducts(ShoppingCart shoppingCart) {
        productService.buyProducts(shoppingCart.getProducts());
    }

    private boolean checkMemberShip() {
        Answer answer = InputView.readMemberShipOption();
        if (answer.isYes()) {
            return true;
        }
        return false;
    }


    private boolean shouldRetry() {
        Answer answer = InputView.readRetry();
        return answer.isYes();
    }
}