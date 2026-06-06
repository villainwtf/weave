package wtf.villain.weave.util;

import org.jetbrains.annotations.NotNull;

public final class InvalidFormatException extends IllegalArgumentException {

    public InvalidFormatException(@NotNull String message) {
        super(message);
    }
}
