package wtf.villain.weave;

import org.jetbrains.annotations.NotNull;
import wtf.villain.weave.builder.WeaveInstanceBuilder;
import wtf.villain.weave.client.TolgeeClient;
import wtf.villain.weave.storage.Project;
import wtf.villain.weave.storage.Storage;

import java.util.concurrent.CompletableFuture;

public sealed interface Weave permits Weave.Impl {

    @NotNull
    static WeaveInstanceBuilder builder() {
        return new WeaveInstanceBuilder();
    }

    /**
     * Gets the underlying Retrofit client.
     *
     * @return the client
     */
    @NotNull
    TolgeeClient client();

    /**
     * Gets the underlying storage instance.
     * This is where all the supported languages and translations are cached.
     *
     * @return the storage instance
     */
    @NotNull
    Storage storage();

    /**
     * Refreshes the cache in the background.
     *
     * @return a future that completes when the cache is refreshed
     */
    @NotNull
    default CompletableFuture<Void> refresh() {
        return storage().refresh(client());
    }

    /**
     * Refreshes the cache for the given project in the background.
     *
     * @param projectId the ID of the project to refresh
     * @return a future that completes when the cache is refreshed
     */
    @NotNull
    default CompletableFuture<Project> refreshProject(int projectId) {
        return storage().refreshProject(client(), projectId);
    }

    /**
     * Gets the project with the given ID.
     *
     * @param id the ID of the project
     * @return the project
     */
    @NotNull
    default Project project(int id) {
        return storage().ensureProject(id);
    }

    /**
     * Closes the underlying client.
     */
    void dispose();

    record Impl(@NotNull TolgeeClient client,
                @NotNull Runnable clientShutdown,
                @NotNull Storage storage) implements Weave {
        @Override
        public void dispose() {
            clientShutdown.run();
        }
    }


}
