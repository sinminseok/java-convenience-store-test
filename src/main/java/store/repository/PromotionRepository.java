package store.repository;

import store.domain.promotion.Promotion;
import store.utils.FileManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PromotionRepository {
    private static final List<Promotion> PROMOTIONS = new ArrayList<>();

    static {
        loadPromotionsFromFile("promotions.md");
    }

    public static List<Promotion> findAll(){
        return Collections.unmodifiableList(PROMOTIONS);
    }

    private static void loadPromotionsFromFile(String fileName) {
        FileManager.readFile(fileName).stream()
                .filter(line -> !line.trim().isEmpty())
                .map(PromotionRepository::parseLineToPromotion)
                .forEach(PROMOTIONS::add);
    }

    public static Optional<Promotion> findByName(String name){
        return PROMOTIONS.stream()
                .filter(promotion -> promotion.isSameName(name))
                .findFirst();
    }

    private static Promotion parseLineToPromotion(String line) {
        String[] values = line.split(",");
        String name = values[0].trim();
        int buy = Integer.parseInt(values[1].trim());
        int get = Integer.parseInt(values[2].trim());
        String startDate = values[3].trim();
        String endDate = values[4].trim();
        return Promotion.of(name, buy, get, startDate, endDate);
    }
}
