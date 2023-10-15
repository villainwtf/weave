package wtf.villain.weave.storage;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Represents a specific language.
 */
public interface Language {

    Map<String, Language> LANGUAGES = new HashMap<>();

    Language ENGLISH = create("English", "en");
    Language GERMAN = create("German", "de");
    Language FRENCH = create("French", "fr");
    Language SPANISH = create("Spanish", "es");
    Language ITALIAN = create("Italian", "it");
    Language JAPANESE = create("Japanese", "ja");
    Language KOREAN = create("Korean", "ko");
    Language CHINESE = create("Chinese", "zh");
    Language RUSSIAN = create("Russian", "ru");
    Language PORTUGUESE = create("Portuguese", "pt");
    Language POLISH = create("Polish", "pl");
    Language DUTCH = create("Dutch", "nl");
    Language SWEDISH = create("Swedish", "sv");
    Language DANISH = create("Danish", "da");
    Language NORWEGIAN = create("Norwegian", "no");
    Language FINNISH = create("Finnish", "fi");
    Language CZECH = create("Czech", "cs");
    Language HUNGARIAN = create("Hungarian", "hu");
    Language TURKISH = create("Turkish", "tr");
    Language GREEK = create("Greek", "el");
    Language ROMANIAN = create("Romanian", "ro");
    Language BULGARIAN = create("Bulgarian", "bg");
    Language CROATIAN = create("Croatian", "hr");
    Language SLOVAK = create("Slovak", "sk");
    Language LITHUANIAN = create("Lithuanian", "lt");
    Language SLOVENIAN = create("Slovenian", "sl");
    Language LATVIAN = create("Latvian", "lv");
    Language ESTONIAN = create("Estonian", "et");
    Language UKRAINIAN = create("Ukrainian", "uk");
    Language SERBIAN = create("Serbian", "sr");
    Language INDONESIAN = create("Indonesian", "id");
    Language THAI = create("Thai", "th");
    Language HINDI = create("Hindi", "hi");

    @NotNull
    static Language findOrCreate(@NotNull String tag, @NotNull String name) {
        return LANGUAGES.computeIfAbsent(tag, __ -> create(name, tag));
    }

    @NotNull
    static Language create(@NotNull String name, @NotNull String tag) {
        Language language = new Impl(name, tag);
        LANGUAGES.put(tag, language);
        return language;
    }

    @NotNull
    static Language fromLocale(@NotNull Locale locale) {
        return findOrCreate(locale.getLanguage(), locale.getDisplayLanguage());
    }

    /**
     * Returns the name of the language.
     *
     * @return the name
     */
    @NotNull
    String name();

    /**
     * Returns the tag of the language.
     *
     * @return the tag
     */
    @NotNull
    String tag();

    /**
     * Returns a map entry of the language.
     *
     * @return the map entry
     */
    @NotNull
    default Map.Entry<String, Language> entry() {
        return Map.entry(tag(), this);
    }

    record Impl(@NotNull String name, @NotNull String tag) implements Language {
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Language language)) return false;
            return language.tag().equals(tag);
        }
    }

}
