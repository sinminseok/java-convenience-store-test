package store.domain.promotion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PromotionPeriod {

    private final LocalDate startDateTime;
    private final LocalDate endDateTime;

    private PromotionPeriod(LocalDate startDateTime, LocalDate endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static PromotionPeriod of(String startDateTime, String endDateTime){
        LocalDate start = LocalDate.parse(startDateTime, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate end = LocalDate.parse(endDateTime, DateTimeFormatter.ISO_LOCAL_DATE);
        return new PromotionPeriod(start, end);
    }

    public boolean isDateInPeriod(LocalDate date) {
        return !date.isBefore(startDateTime) && !date.isAfter(endDateTime);
    }

}
