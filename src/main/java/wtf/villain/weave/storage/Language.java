package wtf.villain.weave.storage;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a specific language.
 */
@SuppressWarnings("unused")
public interface Language {

    Map<String, Language> LANGUAGES = new ConcurrentHashMap<>();

    Language AFAR = create("Afar", "aa");
    Language ABKHAZIAN = create("Abkhazian", "ab");
    Language AVESTAN = create("Avestan", "ae");
    Language AFRIKAANS = create("Afrikaans", "af");
    Language AKAN = create("Akan", "ak");
    Language AMHARIC = create("Amharic", "am");
    Language ARAGONESE = create("Aragonese", "an");
    Language ARABIC = create("Arabic", "ar");
    Language ASSAMESE = create("Assamese", "as");
    Language AVARIC = create("Avaric", "av");
    Language AYMARA = create("Aymara", "ay");
    Language AZERBAIJANI = create("Azerbaijani", "az");
    Language BASHKIR = create("Bashkir", "ba");
    Language BELARUSIAN = create("Belarusian", "be");
    Language BULGARIAN = create("Bulgarian", "bg");
    Language BIHARI = create("Bihari", "bh");
    Language BISLAMA = create("Bislama", "bi");
    Language BAMBARA = create("Bambara", "bm");
    Language BANGLA = create("Bangla", "bn");
    Language TIBETAN = create("Tibetan", "bo");
    Language BRETON = create("Breton", "br");
    Language BOSNIAN = create("Bosnian", "bs");
    Language CATALAN = create("Catalan", "ca");
    Language CHECHEN = create("Chechen", "ce");
    Language CHAMORRO = create("Chamorro", "ch");
    Language CORSICAN = create("Corsican", "co");
    Language CREE = create("Cree", "cr");
    Language CZECH = create("Czech", "cs");
    Language CHURCH_SLAVIC = create("Church Slavic", "cu");
    Language CHUVASH = create("Chuvash", "cv");
    Language WELSH = create("Welsh", "cy");
    Language DANISH = create("Danish", "da");
    Language GERMAN = create("German", "de");
    Language DIVEHI = create("Divehi", "dv");
    Language DZONGKHA = create("Dzongkha", "dz");
    Language EWE = create("Ewe", "ee");
    Language GREEK = create("Greek", "el");
    Language ENGLISH = create("English", "en");
    Language ESPERANTO = create("Esperanto", "eo");
    Language SPANISH = create("Spanish", "es");
    Language ESTONIAN = create("Estonian", "et");
    Language BASQUE = create("Basque", "eu");
    Language PERSIAN = create("Persian", "fa");
    Language FULA = create("Fula", "ff");
    Language FINNISH = create("Finnish", "fi");
    Language FIJIAN = create("Fijian", "fj");
    Language FAROESE = create("Faroese", "fo");
    Language FRENCH = create("French", "fr");
    Language WESTERN_FRISIAN = create("Western Frisian", "fy");
    Language IRISH = create("Irish", "ga");
    Language SCOTTISH_GAELIC = create("Scottish Gaelic", "gd");
    Language GALICIAN = create("Galician", "gl");
    Language GUARANI = create("Guarani", "gn");
    Language GUJARATI = create("Gujarati", "gu");
    Language MANX = create("Manx", "gv");
    Language HAUSA = create("Hausa", "ha");
    Language HEBREW = create("Hebrew", "he");
    Language HINDI = create("Hindi", "hi");
    Language HIRI_MOTU = create("Hiri Motu", "ho");
    Language CROATIAN = create("Croatian", "hr");
    Language HAITIAN_CREOLE = create("Haitian Creole", "ht");
    Language HUNGARIAN = create("Hungarian", "hu");
    Language ARMENIAN = create("Armenian", "hy");
    Language HERERO = create("Herero", "hz");
    Language INTERLINGUA = create("Interlingua", "ia");
    Language INDONESIAN = create("Indonesian", "id");
    Language INTERLINGUE = create("Interlingue", "ie");
    Language IGBO = create("Igbo", "ig");
    Language SICHUAN_YI = create("Sichuan Yi", "ii");
    Language INUPIAQ = create("Inupiaq", "ik");
    Language INDONESIAN_LEGACY = create("Indonesian", "in");
    Language IDO = create("Ido", "io");
    Language ICELANDIC = create("Icelandic", "is");
    Language ITALIAN = create("Italian", "it");
    Language INUKTITUT = create("Inuktitut", "iu");
    Language HEBREW_LEGACY = create("Hebrew", "iw");
    Language JAPANESE = create("Japanese", "ja");
    Language YIDDISH_LEGACY = create("Yiddish", "ji");
    Language JAVANESE = create("Javanese", "jv");
    Language GEORGIAN = create("Georgian", "ka");
    Language KONGO = create("Kongo", "kg");
    Language KIKUYU = create("Kikuyu", "ki");
    Language KUANYAMA = create("Kuanyama", "kj");
    Language KAZAKH = create("Kazakh", "kk");
    Language KALAALLISUT = create("Kalaallisut", "kl");
    Language KHMER = create("Khmer", "km");
    Language KANNADA = create("Kannada", "kn");
    Language KOREAN = create("Korean", "ko");
    Language KANURI = create("Kanuri", "kr");
    Language KASHMIRI = create("Kashmiri", "ks");
    Language KURDISH = create("Kurdish", "ku");
    Language KOMI = create("Komi", "kv");
    Language CORNISH = create("Cornish", "kw");
    Language KYRGYZ = create("Kyrgyz", "ky");
    Language LATIN = create("Latin", "la");
    Language LUXEMBOURGISH = create("Luxembourgish", "lb");
    Language GANDA = create("Ganda", "lg");
    Language LIMBURGISH = create("Limburgish", "li");
    Language LINGALA = create("Lingala", "ln");
    Language LAO = create("Lao", "lo");
    Language LITHUANIAN = create("Lithuanian", "lt");
    Language LUBA_KATANGA = create("Luba-Katanga", "lu");
    Language LATVIAN = create("Latvian", "lv");
    Language MALAGASY = create("Malagasy", "mg");
    Language MARSHALLESE = create("Marshallese", "mh");
    Language MAORI = create("Māori", "mi");
    Language MACEDONIAN = create("Macedonian", "mk");
    Language MALAYALAM = create("Malayalam", "ml");
    Language MONGOLIAN = create("Mongolian", "mn");
    Language MOLDAVIAN = create("Moldavian", "mo");
    Language MARATHI = create("Marathi", "mr");
    Language MALAY = create("Malay", "ms");
    Language MALTESE = create("Maltese", "mt");
    Language BURMESE = create("Burmese", "my");
    Language NAURU = create("Nauru", "na");
    Language NORWEGIAN_BOKMAL = create("Norwegian Bokmål", "nb");
    Language NORTH_NDEBELE = create("North Ndebele", "nd");
    Language NEPALI = create("Nepali", "ne");
    Language NDONGA = create("Ndonga", "ng");
    Language DUTCH = create("Dutch", "nl");
    Language NORWEGIAN_NYNORSK = create("Norwegian Nynorsk", "nn");
    Language NORWEGIAN = create("Norwegian", "no");
    Language SOUTH_NDEBELE = create("South Ndebele", "nr");
    Language NAVAJO = create("Navajo", "nv");
    Language NYANJA = create("Nyanja", "ny");
    Language OCCITAN = create("Occitan", "oc");
    Language OJIBWA = create("Ojibwa", "oj");
    Language OROMO = create("Oromo", "om");
    Language ODIA = create("Odia", "or");
    Language OSSETIC = create("Ossetic", "os");
    Language PUNJABI = create("Punjabi", "pa");
    Language PALI = create("Pali", "pi");
    Language POLISH = create("Polish", "pl");
    Language PASHTO = create("Pashto", "ps");
    Language PORTUGUESE = create("Portuguese", "pt");
    Language QUECHUA = create("Quechua", "qu");
    Language ROMANSH = create("Romansh", "rm");
    Language RUNDI = create("Rundi", "rn");
    Language ROMANIAN = create("Romanian", "ro");
    Language RUSSIAN = create("Russian", "ru");
    Language KINYARWANDA = create("Kinyarwanda", "rw");
    Language SANSKRIT = create("Sanskrit", "sa");
    Language SARDINIAN = create("Sardinian", "sc");
    Language SINDHI = create("Sindhi", "sd");
    Language NORTHERN_SAMI = create("Northern Sami", "se");
    Language SANGO = create("Sango", "sg");
    Language SINHALA = create("Sinhala", "si");
    Language SLOVAK = create("Slovak", "sk");
    Language SLOVENIAN = create("Slovenian", "sl");
    Language SAMOAN = create("Samoan", "sm");
    Language SHONA = create("Shona", "sn");
    Language SOMALI = create("Somali", "so");
    Language ALBANIAN = create("Albanian", "sq");
    Language SERBIAN = create("Serbian", "sr");
    Language SWATI = create("Swati", "ss");
    Language SOUTHERN_SOTHO = create("Southern Sotho", "st");
    Language SUNDANESE = create("Sundanese", "su");
    Language SWEDISH = create("Swedish", "sv");
    Language SWAHILI = create("Swahili", "sw");
    Language TAMIL = create("Tamil", "ta");
    Language TELUGU = create("Telugu", "te");
    Language TAJIK = create("Tajik", "tg");
    Language THAI = create("Thai", "th");
    Language TIGRINYA = create("Tigrinya", "ti");
    Language TURKMEN = create("Turkmen", "tk");
    Language TAGALOG = create("Tagalog", "tl");
    Language TSWANA = create("Tswana", "tn");
    Language TONGAN = create("Tongan", "to");
    Language TURKISH = create("Turkish", "tr");
    Language TSONGA = create("Tsonga", "ts");
    Language TATAR = create("Tatar", "tt");
    Language TWI = create("Twi", "tw");
    Language TAHITIAN = create("Tahitian", "ty");
    Language UYGHUR = create("Uyghur", "ug");
    Language UKRAINIAN = create("Ukrainian", "uk");
    Language URDU = create("Urdu", "ur");
    Language UZBEK = create("Uzbek", "uz");
    Language VENDA = create("Venda", "ve");
    Language VIETNAMESE = create("Vietnamese", "vi");
    Language VOLAPUK = create("Volapük", "vo");
    Language WALLOON = create("Walloon", "wa");
    Language WOLOF = create("Wolof", "wo");
    Language XHOSA = create("Xhosa", "xh");
    Language YIDDISH = create("Yiddish", "yi");
    Language YORUBA = create("Yoruba", "yo");
    Language ZHUANG = create("Zhuang", "za");
    Language CHINESE = create("Chinese", "zh");
    Language ZULU = create("Zulu", "zu");

    /**
     * Normalizes language and locale tags to BCP 47 form.
     * <p>
     * Examples: {@code en} stays {@code en}, {@code en_US} becomes {@code en-US}.
     * Legacy language-only tags such as {@code iw}, {@code in}, and {@code ji} are preserved.
     *
     * @param tag the language or locale tag
     * @return the normalized tag
     */
    @NotNull
    static String normalizeTag(@NotNull String tag) {
        String trimmed = tag.trim();

        if (trimmed.indexOf('_') == -1 && trimmed.indexOf('-') == -1) {
            return trimmed.toLowerCase(Locale.ROOT);
        }

        String languageTag = trimmed.replace('_', '-');
        String normalized = Locale.forLanguageTag(languageTag).toLanguageTag();

        if ("und".equals(normalized)) {
            return languageTag;
        }

        return normalized;
    }

    @NotNull
    static Language findOrCreate(@NotNull String tag, @NotNull String name) {
        String normalizedTag = normalizeTag(tag);
        Language existing = LANGUAGES.get(normalizedTag);

        if (existing != null) {
            registerAliases(tag, existing);
            return existing;
        }

        Language language = new Impl(name, normalizedTag);
        Language previous = LANGUAGES.putIfAbsent(normalizedTag, language);
        Language resolved = previous != null ? previous : language;
        registerAliases(tag, resolved);
        return resolved;
    }

    @NotNull
    static Language create(@NotNull String name, @NotNull String tag) {
        return findOrCreate(tag, name);
    }

    @NotNull
    static Language fromLocale(@NotNull Locale locale) {
        return findOrCreate(locale.toLanguageTag(), locale.getDisplayName(Locale.ENGLISH));
    }

    private static void registerAliases(@NotNull String tag, @NotNull Language language) {
        String trimmed = tag.trim();
        if (trimmed.isEmpty()) return;

        LANGUAGES.putIfAbsent(trimmed, language);

        String normalizedTag = language.tag();
        LANGUAGES.putIfAbsent(normalizedTag, language);

        if (normalizedTag.indexOf('-') != -1) {
            String javaLocaleTag = normalizedTag.replace('-', '_');
            LANGUAGES.putIfAbsent(javaLocaleTag, language);
            LANGUAGES.putIfAbsent(javaLocaleTag.toLowerCase(Locale.ROOT), language);
            LANGUAGES.putIfAbsent(normalizedTag.toLowerCase(Locale.ROOT), language);
        }
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
            return normalizeTag(language.tag()).equals(tag);
        }

        @Override
        public int hashCode() {
            return tag.hashCode();
        }
    }

}
