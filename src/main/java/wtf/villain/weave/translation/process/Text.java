package wtf.villain.weave.translation.process;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import wtf.villain.weave.translation.Translation;

@Getter
@Setter
@AllArgsConstructor
public final class Text {
    private final @NotNull Translation translation;
    private final Object @NotNull [] formats;
    private @NotNull String text;
}
