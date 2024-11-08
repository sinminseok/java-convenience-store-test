package store;

import store.config.AppConfig;
import store.controller.ConvenienceController;

public class Application {
    public static void main(String[] args) {
        ConvenienceController convenienceController = new ConvenienceController(AppConfig.getProductService(), AppConfig.getOrderService());

        convenienceController.run();

    }
}
