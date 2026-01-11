@file:Suppress("UnstableApiUsage", "SpellCheckingInspection")

plugins {
    alias(libs.plugins.shadow)
}

architectury { neoForge() }

val shadowBundle: Configuration by configurations.getting
val developmentNeoForge: Configuration by configurations.getting
configurations {
    developmentNeoForge.extendsFrom(common.get())
}

repositories {
    maven("https://maven.neoforged.net/releases") { name = "NeoForged" }
}

dependencies {
    neoForge(libs.neoforge.neoforge)

    localRuntime(libs.neoforge.mixinextras)

    modImplementation(libs.neoforge.tacz)
    modImplementation(libs.neoforge.kubejs)

    forgeRuntimeLibrary(libs.commons.math3)
    forgeRuntimeLibrary(libs.luaj.core)
    forgeRuntimeLibrary(libs.luaj.jse)
    modLocalRuntime(libs.neoforge.playeranimator)
    modLocalRuntime(libs.neoforge.jei)
}

tasks {
    shadowJar {
        configurations = listOf(shadowBundle)
        archiveClassifier.set("dev-shadow")

        mergeServiceFiles()
    }

    remapJar {
        inputFile.set(shadowJar.flatMap { it.archiveFile })
        dependsOn(shadowJar)
    }
}
