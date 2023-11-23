package wtf.villain.weave.translation;

import org.junit.jupiter.api.Test;
import wtf.villain.weave.translation.process.WeaveProcessor;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TranslationTest {

    @Test
    public void testSimple() {
        Translation translation = new Translation("Hello world!");
        assertEquals("Hello world!", translation.format(Map.of()));
    }

    @Test
    public void testVariable() {
        Translation translation = new Translation("Hello {name}!");
        assertEquals("Hello John!", translation.format(Map.of("name", "John")));
    }

    @Test
    public void testVariables() {
        Translation translation = new Translation("Hello {name}!");
        assertEquals("Hello John!", translation.format(Map.of("name", "John")));
        assertEquals("Hello Peter!", translation.format(Map.of("name", "Peter")));
        assertEquals("Hello world!", translation.format(Map.of("name", "world")));
    }

    @Test
    public void testNested() {
        Translation translation = new Translation("""
              {applesCount, plural,\s
                  =0 {{pearsCount, plural,\s
                      =0 {{orangesCount, plural,\s
                          =0 {I have no fruit}
                          other {I have some oranges}
                      }}
                      other {{orangesCount, plural,\s
                          =0 {I have some pears}
                          other {I have some pears and some oranges}
                      }}
                  }}
                  other {{pearsCount, plural,\s
                      =0 {{orangesCount, plural,\s
                          =0 {I have some apples}
                          other {I have some apples and some oranges}
                      }}
                      other {{orangesCount, plural,\s
                          =0 {I have some apples and some pears}
                          other {I have some apples, some pears, and some oranges}
                      }}
                  }}
              }""");
        assertEquals("I have no fruit", translation.format(Map.of("applesCount", 0, "pearsCount", 0, "orangesCount", 0)));
        assertEquals("I have some oranges", translation.format(Map.of("applesCount", 0, "pearsCount", 0, "orangesCount", 1)));
        assertEquals("I have some pears and some oranges", translation.format(Map.of("applesCount", 0, "pearsCount", 1, "orangesCount", 1)));
        assertEquals("I have some apples, some pears, and some oranges", translation.format(Map.of("applesCount", 1, "pearsCount", 1, "orangesCount", 1)));
    }

    @Test
    public void testPreparedVariable() {
        Translation translation = new Translation("Hello {name}!");
        translation.prepare("name");
        assertEquals("Hello John!", translation.format("John"));
    }

    @Test
    public void testComplex() {
        Translation translation = new Translation("{dogs, plural, one {Peter has one dog.} other {Peter has # dogs.}}");
        assertEquals("Peter has 0 dogs.", translation.format(Map.of("dogs", 0)));
        assertEquals("Peter has one dog.", translation.format(Map.of("dogs", 1)));
        assertEquals("Peter has 123 dogs.", translation.format(Map.of("dogs", 123)));
    }

    @Test
    public void testPostProcessor() {
        Translation translation = new Translation("Hello {name}!", WeaveProcessor.of(
              pending -> pending.text().toUpperCase()
        ));
        assertEquals("HELLO JOHN!", translation.format(Map.of("name", "John")));
    }

    @Test
    public void testMultiplePostProcessors() {
        Translation translation = new Translation("Hello world!", WeaveProcessor.of(
              pending -> pending.text().toUpperCase(),
              pending -> pending.text().toLowerCase()
        ));
        assertEquals("hello world!", translation.format(Map.of()));
    }

    @Test
    public void testPreparedObjectsRemainUnchanged() {
        Translation translation = new Translation("Hello world!");
        assertEquals(translation.prepare().hashCode(), translation.prepare().hashCode());
    }

}
