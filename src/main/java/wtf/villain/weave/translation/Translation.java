package wtf.villain.weave.translation;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.villain.weave.translation.process.WeaveProcessor;
import wtf.villain.weave.util.InvalidFormatException;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Getter
public final class Translation {

    @NotNull
    private final String value;

    @NotNull
    private final WeaveProcessor processor;

    @Getter(AccessLevel.NONE)
    @Nullable
    private final Locale locale;

    public Translation(@NotNull String value) {
        this(value, WeaveProcessor.of());
    }

    public Translation(@NotNull String value, @NotNull WeaveProcessor processor) {
        this(value, processor, null);
    }

    private Translation(@NotNull String value, @NotNull WeaveProcessor processor, @Nullable Locale locale) {
        this.value = value;
        this.processor = processor;
        this.locale = locale;
    }

    private final ThreadLocal<PreparedTranslation> prepared = new ThreadLocal<>();

    /**
     * Creates a translation whose ICU message formatting uses a fixed locale.
     *
     * @param value  the ICU message pattern
     * @param locale the formatting locale
     * @return a locale-aware translation
     */
    @NotNull
    public static Translation withLocale(@NotNull String value, @NotNull Locale locale) {
        return withLocale(value, WeaveProcessor.of(), locale);
    }

    /**
     * Creates a processed translation whose ICU message formatting uses a fixed locale.
     *
     * @param value     the ICU message pattern
     * @param processor the processor applied after formatting
     * @param locale    the formatting locale
     * @return a locale-aware translation
     */
    @NotNull
    public static Translation withLocale(
        @NotNull String value,
        @NotNull WeaveProcessor processor,
        @NotNull Locale locale
    ) {
        return new Translation(value, processor, Objects.requireNonNull(locale, "locale"));
    }

    /**
     * Formats the translation with the given arguments.
     * <p>If the translation has not been prepared, it will be prepared using the given keys.
     *
     * @param formats the formats to use
     * @return the formatted translation
     *
     * @throws InvalidFormatException if the number of objects does not match the number of keys
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
     *
     * @throws IllegalStateException  if the translation has not been prepared
     * @throws InvalidFormatException if the number of objects does not match the number of keys
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

    @Nullable
    Locale locale() {
        return locale;
    }

    @Override
    public String toString() {
        return String.format("Translation[value=%s]", value);
    }
}
