package wtf.villain.weave.translation;

import com.ibm.icu.text.MessageFormat;
import org.jetbrains.annotations.NotNull;
import wtf.villain.weave.util.InvalidFormatException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PreparedTranslation {

    private final Translation translation;
    private final String[] keys;
    private final ThreadLocal<State> state;

    PreparedTranslation(@NotNull Translation translation, @NotNull List<String> keys) {
        this.translation = translation;
        this.keys = keys.toArray(new String[0]);
        this.state = ThreadLocal.withInitial(() -> new State(translation.value(), this.keys));
    }

    /**
     * Formats the translation with the given arguments.
     *
     * @param objects the objects to use
     * @return the formatted translation
     *
     * @throws InvalidFormatException if the number of objects does not match the number of keys
     */
    @NotNull
    public String format(@NotNull Object... objects) {
        State state = this.state.get();
        state.set(keys, objects);
        return translation.processor().apply(translation, state.format());
    }

    /**
     * Formats the translation with the given arguments.
     *
     * @param objects the objects to use
     * @return the formatted translation
     *
     * @throws InvalidFormatException if the number of objects does not match the number of keys
     */
    @NotNull
    public String format(@NotNull List<Object> objects) {
        State state = this.state.get();
        state.set(keys, objects);
        return translation.processor().apply(translation, state.format());
    }

    private static final class State {
        private final MessageFormat messageFormat;
        private final Map<String, Object> formats = new HashMap<>();

        private State(@NotNull String value, @NotNull String[] keys) {
            this.messageFormat = new MessageFormat(value);

            for (String key : keys) {
                this.formats.put(key, null);
            }
        }

        private void set(@NotNull String[] keys, @NotNull Object[] objects) {
            if (objects.length < keys.length) {
                throw new InvalidFormatException(String.format(
                    "Expected %d format arguments (keys: %s), but got %d",
                    keys.length,
                    Arrays.toString(keys),
                    objects.length
                ));
            }

            for (int i = 0; i < keys.length; i++) {
                this.formats.put(keys[i], objects[i]);
            }
        }

        private void set(@NotNull String[] keys, @NotNull List<Object> objects) {
            if (objects.size() < keys.length) {
                throw new InvalidFormatException(String.format(
                    "Expected %d format arguments (keys: %s), but got %d",
                    keys.length,
                    Arrays.toString(keys),
                    objects.size()
                ));
            }

            for (int i = 0; i < keys.length; i++) {
                this.formats.put(keys[i], objects.get(i));
            }
        }

        @NotNull
        private String format() {
            return this.messageFormat.format(this.formats);
        }
    }

    @Override
    public String toString() {
        return String.format("PreparedTranslation[translation=%s, keys=%s]", translation, Arrays.toString(keys));
    }
}
