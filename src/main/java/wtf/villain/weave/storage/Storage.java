package wtf.villain.weave.storage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.villain.weave.client.LanguagesResponse;
import wtf.villain.weave.client.TolgeeClient;
import wtf.villain.weave.translation.Translation;
import wtf.villain.weave.translation.process.PostProcessor;
import wtf.villain.weave.translation.process.WeaveProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Getter
@RequiredArgsConstructor
public final class Storage {

    private final List<Integer> projectIds;
    private final List<PostProcessor> postProcessors;
    private final Map<Integer, Project> projects = new HashMap<>();

    /**
     * Gets the project with the given ID.
     *
     * @param id the ID of the project to get
     * @return the project with the given ID
     * @throws IllegalArgumentException if no such project exists
     */
    @NotNull
    public Project ensureProject(int id) {
        Project project = project(id);

        if (project == null) {
            throw new IllegalArgumentException("No project with ID " + id);
        }

        return project;
    }

    /**
     * Gets the project with the given ID.
     *
     * @param id the ID of the project to get
     * @return the project with the given ID, or {@code null} if no such project exists
     */
    @Nullable
    public Project project(int id) {
        return projects.get(id);
    }

    /**
     * Refreshes the cache in the background.
     *
     * @param client the Tolgee client
     * @return a future that completes when the cache is refreshed
     */
    @NotNull
    public CompletableFuture<Void> refresh(@NotNull TolgeeClient client) {
        List<CompletableFuture<Void>> futures = projectIds.stream()
              .map(projectId -> refreshProject(client, projectId)
                    .thenAccept(project -> projects.put(projectId, project)))
              .toList();

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    /**
     * Refreshes the cache of the given project in the background.
     *
     * @param client    the Tolgee client
     * @param projectId the project ID
     * @return a future that completes when the cache is refreshed
     */
    @NotNull
    public CompletableFuture<Project> refreshProject(@NotNull TolgeeClient client, int projectId) {
        CompletableFuture<Project> future = new CompletableFuture<>();

        int oldProjectVersion = Optional.ofNullable(projects.get(projectId))
              .map(Project::version)
              .orElse(0);

        WeaveProcessor processor = WeaveProcessor.of(postProcessors.toArray(PostProcessor[]::new));

        CompletableFuture<List<LanguagesResponse.Language>> languagesFuture = client.querySupportedLanguages(client, projectId);
        languagesFuture.whenComplete((languages, throwable) -> {
            if (throwable != null) {
                future.completeExceptionally(throwable);
                return;
            }

            if (languages.isEmpty()) {
                // If there are no languages, there won't be any translations either.
                future.complete(new Project(projectId, Map.of(), Map.of(), oldProjectVersion + 1));
                return;
            }

            Map<String, Map<String, Translation>> translations = new HashMap<>();

            List<CompletableFuture<Void>> languageFutures = languages.stream()
                  .map(language -> client.queryTranslations(client, projectId, language.tag())
                        .thenAccept(map -> {
                            Map<String, String> keyToValue = map.get(language.tag());

                            if (keyToValue == null) {
                                // This should never happen since we're querying the translations for the given language.
                                throw new IllegalStateException("No translations for language " + language.tag());
                            }

                            keyToValue.forEach((key, value) -> {
                                // We iterate through each (translation key -> text) pair and add it to the map.
                                translations.computeIfAbsent(language.tag(), __ -> new HashMap<>())
                                      .put(key, new Translation(value, processor));
                            });
                        }))
                  .toList();

            CompletableFuture.allOf(languageFutures.toArray(CompletableFuture[]::new))
                  .whenComplete((unused, throwable1) -> {
                      if (throwable1 != null) {
                          future.completeExceptionally(throwable1);
                          return;
                      }

                      future.complete(new Project(
                            projectId,
                            languages.stream().collect(HashMap::new, (map, language) -> map.put(language.tag(), Language.findOrCreate(language.tag(), language.name())), HashMap::putAll),
                            translations,
                            oldProjectVersion + 1));
                  });
        });

        return future;
    }

}
