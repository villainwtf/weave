package wtf.villain.weave.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class LanguagesResponse {

    @JsonProperty("_embedded")
    private LanguagesContainer embedded;

    @Override
    public String toString() {
        return embedded.toString();
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class LanguagesContainer {
        @JsonProperty("languages")
        private List<Language> languages;

        @Override
        public String toString() {
            return languages.toString();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Language {
        @JsonProperty("id")
        private int id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("tag")
        private String tag;

        @JsonProperty("originalName")
        private String originalName;

        @JsonProperty("flagEmoji")
        private String flagEmoji;

        @JsonProperty("base")
        private boolean base;

        @Override
        public String toString() {
            return String.format("Language[id=%d, name=%s, tag=%s, originalName=%s, flagEmoji=%s, base=%b]",
                  id, name, tag, originalName, flagEmoji, base);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Language language)) return false;
            return language.tag.equals(tag);
        }
    }
}
