package wtf.villain.weave.translation.process;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

@FunctionalInterface
public interface PostProcessor extends Function<Text, String> {

    /**
     * Returns a post-processor that returns its input unchanged.
     *
     * @return a post-processor that returns its input unchanged
     */
    @NotNull
    static PostProcessor identity() {
        return Text::text;
    }

}
