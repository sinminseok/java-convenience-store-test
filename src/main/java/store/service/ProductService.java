package store.service;

import store.domain.cart.OrderProduct;
import store.domain.product.Product;
import store.domain.promotion.PromotionState;
import store.dto.ProductResponse;
import store.mapper.ProductMapper;
import store.dto.ProductRequest;
import store.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 모든 상품 조회 기능
     */
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 상품 주문 메서드
     */
    public void buyProducts(List<OrderProduct> orderProducts){
        for(OrderProduct orderProduct : orderProducts){
            if(orderProduct.isPromotionOrder()){
                Optional<Product> product = productRepository.findPromotionProduct(orderProduct.getProductName());
                product.get().decreaseQuantity(orderProduct.getQuantityValue());
            }
            if(!orderProduct.isPromotionOrder()){
                Optional<Product> product = productRepository.findGeneralProduct(orderProduct.getProductName());
                product.get().decreaseQuantity(orderProduct.getQuantityValue());
            }
        }

    }

    /**
     * 프로모션 상태 확인 메서드
     */
    public PromotionState checkPromotionState(ProductRequest productRequest) {
        Optional<Product> promotionProduct = productRepository.findPromotionProduct(productRequest.name());
        if (promotionProduct.isEmpty()) {
            return PromotionState.NOT_APPLIED;
        }
        //프로모션 재고 부족으로 일부 수량만 혜택 없이 결제해야 하는 경우
        if (!promotionProduct.get().isEnoughPromotionQuantity(productRequest.quantity())) {
            return PromotionState.PARTIAL_APPLIED;
        }
        // 프로모션 적용 가능하나 고객이 해당 수량을 가져오지 않은 경우
        if (promotionProduct.get().canMoreGetFreeProduct(productRequest.quantity())) {
            return PromotionState.ELIGIBLE;
        }
        return PromotionState.APPLIED;
    }


    public int findFreePromotionProduct(ProductRequest productRequest){
        Optional<Product> product = productRepository.findPromotionProduct(productRequest.name());
        return product.get().countMoreFreeItems(productRequest.quantity());
    }

    public int findNotApplyPromotionCount(ProductRequest productRequest){
        Optional<Product> product = productRepository.findPromotionProduct(productRequest.name());
        return product.get().calculateNotPromotionRemainQuantity(productRequest.quantity());
    }


    //증정 받을 수 있는 상품을 추가한다:
    public OrderProduct addFreePromotionProduct(ProductRequest productRequest) {
        Optional<Product> product = productRepository.findPromotionProduct(productRequest.name());
        int freeProductCount = product.get().countMoreFreeItems(productRequest.quantity());
        return OrderProduct.of(product.get(), productRequest.quantity() + freeProductCount, product.get().getPromotion());
    }

    //증정 받을 수 있는 상품을 추가하지 않는다:
    public OrderProduct skipFreePromotionProduct(ProductRequest productRequest) {
        Optional<Product> product = productRepository.findPromotionProduct(productRequest.name());
        return OrderProduct.of(product.get(), productRequest.quantity(), product.get().getPromotion());
    }

    //일부 수량에 대해 정가로 결제한다: 프로모션 남아 있는거 다 사고, 나머지 갯수도 산다.
    public List<OrderProduct> chargePartialQuantityAtFullPrice(ProductRequest productRequest) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        Optional<Product> product = productRepository.findPromotionProduct(productRequest.name());
        //프로모션 남아 있는거 다삼
        orderProducts.add(OrderProduct.of(product.get(), product.get().getQuantityValue(), product.get().getPromotion()));

        //더 구매해야할 수량이 남아 있으면 일반 재고에서 구입한다.
        if(product.get().getQuantityValue() < productRequest.quantity()){
            orderProducts.add(OrderProduct.of(product.get(), productRequest.quantity() - product.get().getQuantityValue(), Optional.empty()));
        }

        return orderProducts;
    }

    //todo
    //정가로 결제해야 하는 수량만큼 제외한 후 결제를 진행한다: 프로모션이 적용 가능한 수량만 산다?
    public OrderProduct proceedWithReducedQuantityPayment(ProductRequest productRequest, int buyQuantity) {
        Optional<Product> product = productRepository.findPromotionProduct(productRequest.name());

        return OrderProduct.of(product.get(), productRequest.quantity() - buyQuantity, product.get().getPromotion());
    }

    public OrderProduct generalPromotionOrder(ProductRequest request){
        Optional<Product> product = productRepository.findPromotionProduct(request.name());
        return OrderProduct.of(product.get(), request.quantity(), product.get().getPromotion());
    }

    public OrderProduct generalOrder(ProductRequest request){
        Optional<Product> product = productRepository.findGeneralProduct(request.name());
        return OrderProduct.of(product.get(), request.quantity(), Optional.empty());
    }

}
