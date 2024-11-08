package store.config;

import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.OrderService;
import store.service.ProductService;
import store.service.PromotionService;

public class AppConfig {

    private static final PromotionRepository promotionRepository = new PromotionRepository();
    private static final ProductRepository productRepository = new ProductRepository();

    public static PromotionService getPromotionService() {
        return new PromotionService(promotionRepository);
    }

    public static ProductService getProductService() {
        return new ProductService(productRepository);
    }

    public static OrderService getOrderService() { return new OrderService(); }
}
