package wtf.villain.weave.translation.process;

import org.jetbrains.annotations.NotNull;
import wtf.villain.weave.translation.Translation;

@FunctionalInterface
public interface WeaveProcessor {

    @NotNull
    static WeaveProcessor of(@NotNull PostProcessor... postProcessors) {
        if (postProcessors.length == 0) {
            return (translation, text, values) -> text;
        }

        return (translation, text, values) -> {
            Text pending = new Text(translation, values, text);

            for (PostProcessor postProcessor : postProcessors) {
                pending.text(postProcessor.apply(pending));
            }

            return pending.text();
        };
    }

    @NotNull
    String apply(@NotNull Translation translation, @NotNull String text, @NotNull Object... values);

}
