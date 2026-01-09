@file:Suppress("UnstableApiUsage", "SpellCheckingInspection")

plugins {
    alias(libs.plugins.shadow)
}

architectury { forge() }

loom {
    forge {
        mixinConfig("taczjs.forge.mixins.json")
    }
}

val shadowBundle: Configuration by configurations.getting
val developmentForge: Configuration by configurations.getting
configurations {
    developmentForge.extendsFrom(common.get())
}

repositories {}

dependencies {
    forge(libs.forge.forge)

    localRuntime(libs.forge.mixinextras)

    modImplementation(libs.forge.tacz)
    modImplementation(libs.forge.kubejs)

    forgeRuntimeLibrary(libs.commons.math3)
    forgeRuntimeLibrary(libs.luaj.core)
    forgeRuntimeLibrary(libs.luaj.jse)
    modLocalRuntime(libs.forge.playeranimator)
    modLocalRuntime(libs.forge.jei)
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
