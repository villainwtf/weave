package wtf.villain.weave.translation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import wtf.villain.weave.translation.process.WeaveProcessor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public final class Translation {

    @NotNull
    private final String value;

    @NotNull
    private final WeaveProcessor processor;

    public Translation(@NotNull String value) {
        this(value, WeaveProcessor.of());
    }

    private final ThreadLocal<PreparedTranslation> prepared = new ThreadLocal<>();

    /**
     * Formats the translation with the given arguments.
     * <p>If the translation has not been prepared, it will be prepared using the given keys.
     *
     * @param formats the formats to use
     * @return the formatted translation
     */
    @NotNull
    public String format(@NotNull Map<String, Object> formats) {
        PreparedTranslation prepared = prepare(formats.keySet());
        return prepared.format(formats.values().toArray());
    }

    /**
     * Formats the translation with the given arguments.
     * <p>The translation must have been prepared before it can be formatted using this method, as the keys are not
     * provided.
     *
     * @param formats the formats to use
     * @return the formatted translation
     * @throws IllegalStateException if the translation has not been prepared
     */
    @NotNull
    public String format(@NotNull Object... formats) {
        PreparedTranslation prepared = this.prepared.get();
        if (prepared == null) throw new IllegalStateException("Translation has not been prepared");
        return prepared.format(formats);
    }

    /**
     * Prepares the translation for formatting.
     * <p>Preparation is required before a translation can be formatted.
     * <p>See {@link PreparedTranslation} for more information.
     *
     * @param keys the keys to prepare the translation with
     * @return the prepared translation
     */
    @NotNull
    public PreparedTranslation prepare(@NotNull List<String> keys) {
        PreparedTranslation prepared = this.prepared.get();
        if (prepared != null) return prepared; // Avoid copying the list if possible

        prepared = new PreparedTranslation(this, keys);
        this.prepared.set(prepared);
        return prepared;
    }

    /**
     * Prepares the translation for formatting.
     * <p>Preparation is required before a translation can be formatted.
     * <p>See {@link PreparedTranslation} for more information.
     *
     * @param keys the keys to prepare the translation with
     * @return the prepared translation
     */
    @NotNull
    public PreparedTranslation prepare(@NotNull Set<String> keys) {
        PreparedTranslation prepared = this.prepared.get();
        if (prepared != null) return prepared; // Avoid copying the set if possible

        return prepare(List.copyOf(keys));
    }

    /**
     * Convenience method for {@link #prepare(List)}.
     * <p>See {@link PreparedTranslation} for more information.
     */
    @NotNull
    public PreparedTranslation prepare(@NotNull String... keys) {
        PreparedTranslation prepared = this.prepared.get();
        if (prepared != null) return prepared; // Avoid copying the array if possible

        return prepare(List.of(keys));
    }

    @Override
    public String toString() {
        return String.format("Translation[value=%s]", value);
    }
}
