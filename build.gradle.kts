import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.vanniktech.maven.publish") version "0.34.0"
    id("com.gradleup.shadow") version ("9.1.0")
}

group = "wtf.villain"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.42")
    compileOnly("org.projectlombok:lombok:1.18.42")

    implementation("org.jetbrains:annotations:26.0.2")

    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-jackson:3.0.0")

    implementation("com.ibm.icu:icu4j:78.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<ShadowJar> {
    minimizeJar = true

    relocate("com.fasterxml.jackson", "wtf.villain.weave.lib.jackson")
    relocate("com.ibm.icu", "wtf.villain.weave.lib.icu")
    relocate("okhttp3", "wtf.villain.weave.lib.okhttp3")
    relocate("okio", "wtf.villain.weave.lib.okio")
    relocate("org.intellij.lang.annotations", "wtf.villain.weave.lib.intellij")
    relocate("org.jetbrains.annotations", "wtf.villain.weave.lib.jetbrains")
    relocate("retrofit2", "wtf.villain.weave.lib.retrofit2")
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates(
        groupId = project.group.toString(),
        artifactId = project.name,
        version = project.version.toString()
    )

    pom {
        name.set(project.name)
        description.set("A Java library for interacting with the Tolgee API.")

        inceptionYear.set("2024")
        url.set("https://github.com/villainwtf/weave/")

        licenses {
            license {
                name.set("MIT License")
                url.set("http://www.opensource.org/licenses/mit-license.php")
                distribution.set("http://www.opensource.org/licenses/mit-license.php")
            }
        }

        developers {
            developer {
                id.set("villain")
                name.set("Villain developers")
                url.set("https://github.com/villainwtf")
            }
        }

        scm {
            url.set("https://github.com/villainwtf/weave")
            connection.set("scm:git:git://github.com/villainwtf/weave.git")
            developerConnection.set("scm:git:ssh://git@github.com/villainwtf/weave.git")
        }
    }
}
