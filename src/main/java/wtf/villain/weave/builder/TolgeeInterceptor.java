package wtf.villain.weave.builder;

import lombok.AllArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@AllArgsConstructor
public class TolgeeInterceptor implements Interceptor {

    private final String apiKey;

    @Override
    public @NotNull Response intercept(@NotNull Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder()
              .addHeader("X-API-Key", apiKey)
              .build());
    }
}
