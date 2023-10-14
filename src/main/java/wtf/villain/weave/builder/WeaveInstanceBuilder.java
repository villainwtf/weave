package wtf.villain.weave.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import wtf.villain.weave.Weave;
import wtf.villain.weave.client.TolgeeClient;
import wtf.villain.weave.storage.Storage;
import wtf.villain.weave.translation.process.PostProcessor;
import wtf.villain.weave.util.Ensure;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class WeaveInstanceBuilder {

    @Nullable
    private String apiKey;

    @Nullable
    private String endpoint;

    @NotNull
    private final List<Integer> projectIds = new ArrayList<>();

    @NotNull
    private Duration connectTimeout = Duration.ofSeconds(30);

    @NotNull
    private Duration readTimeout = Duration.ofSeconds(30);

    @NotNull
    private Duration writeTimeout = Duration.ofSeconds(30);

    @NotNull
    private final List<PostProcessor> processors = new ArrayList<>();

    /**
     * Sets the API key to use for the Tolgee client.
     *
     * @param apiKey the API key
     * @return the builder
     */
    @NotNull
    public WeaveInstanceBuilder apiKey(@NotNull String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    /**
     * Sets the endpoint to use for the Tolgee client.
     *
     * @param endpoint the endpoint
     * @return the builder
     */
    @NotNull
    public WeaveInstanceBuilder endpoint(@NotNull String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    /**
     * Adds the given project IDs to the Tolgee client.
     *
     * @param projectIds the project IDs
     * @return the builder
     */
    @NotNull
    public WeaveInstanceBuilder addProjects(int... projectIds) {
        for (int projectId : projectIds) {
            this.projectIds.add(projectId);
        }
        return this;
    }

    /**
     * Sets the connect timeout for the Tolgee client.
     *
     * @param connectTimeout the connect timeout
     * @return the builder
     */
    @NotNull
    public WeaveInstanceBuilder connectTimeout(@NotNull Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    /**
     * Sets the read timeout for the Tolgee client.
     *
     * @param readTimeout the read timeout
     * @return the builder
     */
    @NotNull
    public WeaveInstanceBuilder readTimeout(@NotNull Duration readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    /**
     * Sets the write timeout for the Tolgee client.
     *
     * @param writeTimeout the write timeout
     * @return the builder
     */
    @NotNull
    public WeaveInstanceBuilder writeTimeout(@NotNull Duration writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    /**
     * Adds the given translation post processors to the Tolgee client.
     *
     * @param processors the translation post processors
     * @return the builder
     */
    @NotNull
    public WeaveInstanceBuilder addProcessors(@NotNull PostProcessor... processors) {
        this.processors.addAll(Arrays.asList(processors));
        return this;
    }

    /**
     * Builds the Tolgee client synchronously.
     *
     * @return the Tolgee client
     */
    @NotNull
    public Weave build() {
        return buildAsync().join();
    }

    /**
     * Builds the Tolgee client asynchronously.
     *
     * @return a future that completes when the Tolgee client is built
     */
    @NotNull
    public CompletableFuture<Weave> buildAsync() {
        Ensure.argumentIsSet(apiKey, "apiKey");
        Ensure.argumentIsSet(endpoint, "endpoint");
        Ensure.that(!projectIds.isEmpty(), "projectIds must contain at least one project ID");
        Ensure.argumentIsSet(connectTimeout, "connectTimeout");
        Ensure.argumentIsSet(readTimeout, "readTimeout");
        Ensure.argumentIsSet(writeTimeout, "writeTimeout");
        Ensure.that(connectTimeout.toMillis() > 0, "connectTimeout must be greater than zero");
        Ensure.that(readTimeout.toMillis() > 0, "readTimeout must be greater than zero");
        Ensure.that(writeTimeout.toMillis() > 0, "writeTimeout must be greater than zero");

        CompletableFuture<Weave> future = new CompletableFuture<>();

        OkHttpClient httpClient = new OkHttpClient.Builder()
              .addInterceptor(new TolgeeInterceptor(apiKey))
              .connectTimeout(connectTimeout)
              .readTimeout(readTimeout)
              .writeTimeout(writeTimeout)
              .build();

        TolgeeClient tolgeeClient = new Retrofit.Builder()
              .client(httpClient)
              .baseUrl(endpoint)
              .addConverterFactory(JacksonConverterFactory.create(new ObjectMapper()))
              .build()
              .create(TolgeeClient.class);

        Storage storage = new Storage(projectIds, processors);

        storage.refresh(tolgeeClient).whenComplete((__, throwable) -> {
            if (throwable != null) {
                future.completeExceptionally(throwable);
            } else {
                future.complete(new Weave.Impl(
                      tolgeeClient,
                      () -> {
                          httpClient.dispatcher().executorService().shutdown();
                          httpClient.connectionPool().evictAll();
                      },
                      storage
                ));
            }
        });

        return future;
    }

}
