package store.utils;

import java.util.Optional;

public class OptionalUtils {

    public static <T> T requireNonEmpty(Optional<T> optional, String message) {
        return optional.orElseThrow(() -> new IllegalArgumentException(message));
    }
}