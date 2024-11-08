package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.dto.ProductRequest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputView {

    public static List<ProductRequest> readItem() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String input = Console.readLine();
        return Stream.of(input.split(","))
                .map(item -> {
                    String[] parts = item.replaceAll("[\\[\\]]", "").split("-");
                    return new ProductRequest(parts[0], Integer.parseInt(parts[1]));
                })
                .collect(Collectors.toList());
    }

    public static Answer readMemberShipOption() {
        System.out.println("멤버십 할인을 받으시겠습니까?");
        String input = Console.readLine();
        return Answer.fromCommand(input);
    }

    public static Answer readPromotionOption(String name, int count) {
        System.out.println("현재 " + name + "은(는) " + count + "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        String input = Console.readLine();
        return Answer.fromCommand(input);
    }

    public static Answer readNonPromotionPurchaseOption(String name, int count) {
        System.out.println("현재 " + name + " " + count + " 개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        String input = Console.readLine();
        return Answer.fromCommand(input);
    }

    public static Answer readRetry() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        String input = Console.readLine();
        return Answer.fromCommand(input);
    }
}
