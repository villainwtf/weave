package wtf.villain.weave.translation;

import com.ibm.icu.text.MessageFormat;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PreparedTranslation {

    private final Translation translation;
    private final String[] keys;
    private final Map<String, Object> preparedFormats = new HashMap<>();

    PreparedTranslation(@NotNull Translation translation, @NotNull List<String> keys) {
        this.translation = translation;
        this.keys = keys.toArray(new String[0]);
        keys.forEach(key -> preparedFormats.put(key, null));
    }

    /**
     * Formats the translation with the given arguments.
     *
     * @param objects the objects to use
     * @return the formatted translation
     */
    @NotNull
    public String format(@NotNull Object... objects) {
        MessageFormat messageFormat = new MessageFormat(translation.value());

        for (int i = 0; i < keys.length; i++) {
            preparedFormats.put(keys[i], objects[i]);
        }

        return translation.processor().apply(translation, messageFormat.format(preparedFormats));
    }

    /**
     * Formats the translation with the given arguments.
     *
     * @param objects the objects to use
     * @return the formatted translation
     */
    @NotNull
    public String format(@NotNull List<Object> objects) {
        MessageFormat messageFormat = new MessageFormat(translation.value());

        for (int i = 0; i < keys.length; i++) {
            preparedFormats.put(keys[i], objects.get(i));
        }

        return translation.processor().apply(translation, messageFormat.format(preparedFormats));
    }

    @Override
    public String toString() {
        return String.format("PreparedTranslation[translation=%s, keys=%s]", translation, Arrays.toString(keys));
    }
}
