package wtf.villain.weave.storage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.villain.weave.translation.Translation;

import java.util.Map;

public record Project(int id,
                      Map<String, Language> supportedLanguages,
                      Map<String, Map<String, Translation>> translations,
                      int version) {

    /**
     * Gets the language with the given tag.
     *
     * @param tag the tag of the language to get
     * @return the language with the given tag
     *
     * @throws IllegalArgumentException if the given language is not supported by this project
     */
    @NotNull
    public Language ensureLanguage(@NotNull String tag) {
        Language language = this.language(tag);

        if (language == null) {
            throw new IllegalArgumentException("Language " + tag + " is not supported by project " + this.id());
        }

        return language;
    }

    /**
     * Gets the language with the given tag.
     *
     * @param tag the tag of the language to get
     * @return the language with the given tag, or {@code null} if no such language exists
     */
    @Nullable
    public Language language(@NotNull String tag) {
        return getByTag(this.supportedLanguages, tag);
    }

    /**
     * Checks whether or not the given language tag is supported by this project.
     *
     * @param languageTag the language tag to check
     * @return whether or not the given language tag is supported by this project
     */
    public boolean supports(@NotNull String languageTag) {
        return this.language(languageTag) != null;
    }

    /**
     * Checks whether or not the given language is supported by this project.
     *
     * @param language the language to check
     * @return whether or not the given language is supported by this project
     */
    public boolean supports(@NotNull Language language) {
        return this.supports(language.tag());
    }

    /**
     * Gets the translations for the given language tag. See {@link #translations(Language)}.
     *
     * @param languageTag the language tag to get the translations for
     * @return the translations for the given language tag
     *
     * @throws IllegalArgumentException if the given language is not supported by this project
     * @throws IllegalStateException    if there are no translations for the given language
     */
    @NotNull
    public Map<String, Translation> translations(@NotNull String languageTag) {
        return translations(this.ensureLanguage(languageTag));
    }

    /**
     * Gets the translations for the given language.
     *
     * @param language the language to get the translations for
     * @return the translations for the given language
     *
     * @throws IllegalArgumentException if the given language is not supported by this project
     * @throws IllegalStateException    if there are no translations for the given language
     */
    @NotNull
    public Map<String, Translation> translations(@NotNull Language language) {
        String tag = Language.normalizeTag(language.tag());
        Map<String, Translation> translations = getByTag(this.translations(), tag);

        if (translations == null) {
            if (this.language(tag) != null) {
                throw new IllegalStateException("No translations for language " + tag + " in project " + this.id());
            }

            throw new IllegalArgumentException("Language " + tag + " is not supported by project " + this.id());
        }

        return translations;
    }

    /**
     * Gets the translation for the given language tag and key.
     *
     * @param language the language to get the translation for
     * @param key      the key to get the translation for
     * @return the translation for the given language and key
     *
     * @throws IllegalArgumentException if the given language is not supported by this project
     * @throws IllegalStateException    if there are no translations for the given language
     */
    @Nullable
    public Translation translation(@NotNull Language language, @NotNull String key) {
        return this.translations(language).get(key);
    }

    @Nullable
    private static <T> T getByTag(@NotNull Map<String, T> map, @NotNull String tag) {
        String normalizedTag = Language.normalizeTag(tag);
        T value = map.get(normalizedTag);

        if (value != null) {
            return value;
        }

        value = map.get(tag);

        if (value != null) {
            return value;
        }

        for (Map.Entry<String, T> entry : map.entrySet()) {
            if (Language.normalizeTag(entry.getKey()).equals(normalizedTag)) {
                return entry.getValue();
            }
        }

        return null;
    }
}
