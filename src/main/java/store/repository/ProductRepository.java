package store.repository;

import store.domain.common.Name;
import store.domain.product.Product;
import store.domain.promotion.Promotion;
import store.utils.FileManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductRepository {
    private static final List<Product> PRODUCTS = new ArrayList<>();

    static {
        PromotionRepository.findAll();
        loadProductsFromFile("products.md");
    }

    private static void loadProductsFromFile(String fileName) {
        FileManager.readFile(fileName).stream()
                .filter(line -> !line.trim().isEmpty())
                .map(ProductRepository::parseLineToProduct)
                .forEach(PRODUCTS::add);
    }


    public List<Product> findAll() {
        return Collections.unmodifiableList(PRODUCTS);
    }

    public List<Product> findByName(Name name){
        return PRODUCTS.stream()
                .filter(product -> product.isSameName(name.getValue()))
                .collect(Collectors.toList());
    }

    public Optional<Product> findPromotionProduct(String name) {
        return PRODUCTS.stream()
                .filter(product -> product.isSameName(name))
                .filter(product -> product.isPromotionProduct())
                .filter(product -> product.isPromotionPeriod(LocalDate.now()))
                .findFirst();
    }

    public Optional<Product> findGeneralProduct(String name) {
        return PRODUCTS.stream()
                .filter(product -> product.isSameName(name))
                .filter(product -> !product.isPromotionProduct())
                .findFirst();
    }


    private static Product parseLineToProduct(String line) {
        String[] values = line.split(",");
        String name = values[0].trim();
        int price = Integer.parseInt(values[1].trim());
        int quantity = Integer.parseInt(values[2].trim());
        Optional<Promotion> promotionOptional = PromotionRepository.findByName(values[3].trim());
        return Product.of(name, price, quantity, promotionOptional);
    }
}
