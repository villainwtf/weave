package wtf.villain.weave.storage;

import org.junit.jupiter.api.Test;
import wtf.villain.weave.translation.Translation;

import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class LanguageTest {

    @Test
    public void testLocaleTagNormalization() {
        Language language = Language.findOrCreate("en_us", "English (United States)");

        assertEquals("en-US", language.tag());
        assertSame(language, Language.findOrCreate("en-US", "English (US)"));
        assertSame(language, Language.findOrCreate("en_US", "English (US)"));
        assertSame(language, Language.LANGUAGES.get("en_us"));
        assertSame(language, Language.LANGUAGES.get("en_US"));
    }

    @Test
    public void testFromLocaleUsesLocaleTag() {
        assertEquals("en-US", Language.fromLocale(Locale.US).tag());
        assertEquals("en", Language.fromLocale(Locale.ENGLISH).tag());
    }

    @Test
    public void testProjectAcceptsEquivalentLocaleTags() {
        Language language = Language.findOrCreate("en_US", "English (United States)");
        Project project = new Project(
            1,
            Map.of("en_US", language),
            Map.of("en_US", Map.of("hello", new Translation("Hello"))),
            1
        );

        assertTrue(project.supports("en_US"));
        assertTrue(project.supports("en_us"));
        assertTrue(project.supports("en-US"));
        assertSame(language, project.language("en_us"));
        assertEquals("Hello", project.translations("en_US").get("hello").format(Map.of()));
    }

}
