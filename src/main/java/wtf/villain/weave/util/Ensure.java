package wtf.villain.weave.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Ensure {

    /**
     * Checks that the specified object reference is not {@code null}.
     *
     * @param object the object reference to check for nullity
     * @param key    the key to use in the exception message
     * @throws IllegalArgumentException if {@code object} is {@code null}
     */
    @Contract("null, _ -> fail")
    static void argumentIsSet(@Nullable Object object, @NotNull String key) {
        that(object != null, key + " must be set");
    }

    /**
     * Checks that the given condition is true.
     *
     * @param condition the condition to check
     * @param message   the message to use in the exception
     * @throws IllegalArgumentException if {@code condition} is {@code false}
     */
    static void that(boolean condition, @NotNull String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }
}
