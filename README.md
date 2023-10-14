# Weave

Weave is a modern and highly efficient Java library designed to streamline language translation and localization of your applications. The library is built around the [Tolgee](https://github.com/tolgee/tolgee-platform) API and it allows for powerful, real-time translations with built-in hot-reloading and formatting features. Weave also supports adding post-processors to your translations.

# Notice

This repository contains software that was originally developed for internal use by our organization. We have chosen to open-source this software in the hopes that it may be of use to others.

Please note the following important points:
- While we are making this software available to the public, we will not be providing external support. If you choose to use this software, please understand that you do so entirely at your own risk.
- Additionally, we will not be accepting any contributions to this project. The source code is available for you to use and modify as you wish, within the bounds of the included license, but we will not be incorporating any changes or enhancements made by external parties.

## Setting Up Weave

Setting up Weave is easy. To create a new instance of Weave, you can use the builder:

```java
Weave weave = Weave.builder()
              .endpoint("https://app.tolgee.io")
              .apiKey("tgpak_yourApiKey")
              .addProjects(1)
              .build();
```

The `build` method blocks until the initial translations are fetched from the Tolgee API. If you want to avoid blocking, you can use the `buildAsync` method instead, which returns a `CompletableFuture<Weave>`.

## Translating Text

After initializing Weave, you can fetch translations by using the project and language instances:

```java
Project project = weave.project(1);
Translation translation = project.translation(Language.ENGLISH, "test-hello"); // "Hello {name}!"
String formatted = translation.format(Map.of("name", "John")); // "Hello John!"
```

Translations are automatically prepared after first use, but this can be done manually as well:

```java
translation.prepare("name");
```

The prepare method returns a `PreparedTranslation` instance, which you may use to format the translation, but it is not required as the original translation instance will automatically use the prepared translation.
After preparing a translation, you can format it by using the `format` method, without the need to provide the formatting parameters again:

```java
String formatted = translation.format("John"); // "Hello John!"
```

We provide a set of common languages, but if you need to create a custom language, you can do so by using the `Language.create` method:

```java
Language language = Language.create("en", "English");
```

You may also use the `Language.fromLocale` method to convert a built-in Java locale to a Weave language:

```java
Language language = Language.fromLocale(Locale.ENGLISH);
```

## Hot-Reloading Translations

Weave provides the ability to refresh or reload your translations at runtime. This can be achieved through:

```java
weave.refresh(); // refresh all projects
weave.refreshProject(1); // refresh specific project via its id
```

These methods return `CompletableFuture<Void>`, and `CompletableFuture<Project>` respectively, which can be used to wait for the refresh to complete.

## Adding Post-Processors

Weave allows you to add post-processors to your translations. This can be achieved through:

```java
Weave weave = Weave.builder()
              ...
              .addProcessors(prepared -> prepared.text().toLowerCase())
              .build();
```

These post-processors are applied to all translations, and they are applied after formatting. This allows you to easily parse colors and other formatting codes.

## Shutting Down Weave

When you are done using Weave, you must shut it down to free up resources and stop the network threads. This can be achieved through:

```java
weave.dispose();
```