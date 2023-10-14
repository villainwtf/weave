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
        return this.supportedLanguages.get(tag);
    }

    /**
     * Checks whether or not the given language tag is supported by this project.
     *
     * @param languageTag the language tag to check
     * @return whether or not the given language tag is supported by this project
     */
    public boolean supports(@NotNull String languageTag) {
        return this.supportedLanguages.containsKey(languageTag);
    }

    /**
     * Checks whether or not the given language is supported by this project.
     *
     * @param language the language to check
     * @return whether or not the given language is supported by this project
     */
    public boolean supports(@NotNull Language language) {
        return this.supportedLanguages.containsKey(language.tag());
    }

    /**
     * Gets the translations for the given language tag. See {@link #translations(Language)}.
     *
     * @param languageTag the language tag to get the translations for
     * @return the translations for the given language tag
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
     * @throws IllegalArgumentException if the given language is not supported by this project
     * @throws IllegalStateException    if there are no translations for the given language
     */
    @NotNull
    public Map<String, Translation> translations(@NotNull Language language) {
        Map<String, Translation> translations = this.translations().get(language.tag());

        if (translations == null) {
            if (this.supportedLanguages.containsKey(language.tag())) {
                throw new IllegalStateException("No translations for language " + language.tag() + " in project " + this.id());
            }

            throw new IllegalArgumentException("Language " + language.tag() + " is not supported by project " + this.id());
        }

        return translations;
    }

    /**
     * Gets the translation for the given language tag and key.
     *
     * @param language the language to get the translation for
     * @param key      the key to get the translation for
     * @return the translation for the given language and key
     * @throws IllegalArgumentException if the given language is not supported by this project
     * @throws IllegalStateException    if there are no translations for the given language
     */
    @Nullable
    public Translation translation(@NotNull Language language, @NotNull String key) {
        return this.translations(language).get(key);
    }
}
