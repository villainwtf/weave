package wtf.villain.weave.client;

import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface TolgeeClient {

    @NotNull
    @GET("/v2/projects/{id}/languages?size=2000")
    Call<LanguagesResponse> getLanguages(@Path("id") int id);

    @NotNull
    @GET("/v2/projects/{id}/translations/{language}?structureDelimiter")
    Call<Map<String, Map<String, String>>> getTranslations(@Path("id") int id, @Path("language") String language);

    /**
     * Queries the list of supported languages for the given project.
     *
     * @param client    the Tolgee client
     * @param projectId the project ID
     * @return a future that completes with the list of supported languages
     */
    @NotNull
    default CompletableFuture<List<LanguagesResponse.Language>> querySupportedLanguages(@NotNull TolgeeClient client, int projectId) {
        CompletableFuture<List<LanguagesResponse.Language>> future = new CompletableFuture<>();

        client.getLanguages(projectId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NotNull Call<LanguagesResponse> call, @NotNull Response<LanguagesResponse> response) {
                if (!response.isSuccessful()) {
                    future.completeExceptionally(new RuntimeException("Unsuccessful request, status code is " + response.code()));
                    return;
                }

                LanguagesResponse data = response.body();

                if (data == null) {
                    future.completeExceptionally(new RuntimeException("Invalid response body received"));
                    return;
                }

                future.complete(data.embedded().languages());
            }

            @Override
            public void onFailure(@NotNull Call<LanguagesResponse> call, @NotNull Throwable throwable) {
                future.completeExceptionally(new RuntimeException("Request failed", throwable));
            }
        });

        return future;
    }

    /**
     * Queries the translations for the given project and language.
     *
     * @param client    the Tolgee client
     * @param projectId the project ID
     * @param language  the language
     * @return a future that completes with the translations
     */
    @NotNull
    default CompletableFuture<Map<String, Map<String, String>>> queryTranslations(@NotNull TolgeeClient client, int projectId, @NotNull String language) {
        CompletableFuture<Map<String, Map<String, String>>> future = new CompletableFuture<>();

        client.getTranslations(projectId, language).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NotNull Call<Map<String, Map<String, String>>> call, @NotNull Response<Map<String, Map<String, String>>> response) {
                if (!response.isSuccessful()) {
                    future.completeExceptionally(new RuntimeException("Unsuccessful request, status code is " + response.code()));
                    return;
                }

                Map<String, Map<String, String>> data = response.body();

                if (data == null) {
                    future.completeExceptionally(new RuntimeException("Invalid response body received"));
                    return;
                }

                future.complete(data);
            }

            @Override
            public void onFailure(@NotNull Call<Map<String, Map<String, String>>> call, @NotNull Throwable throwable) {
                future.completeExceptionally(new RuntimeException("Request failed", throwable));
            }
        });

        return future;
    }

}
