@file:Suppress("UnstableApiUsage", "SpellCheckingInspection")

import java.util.zip.ZipFile

plugins {
    alias(libs.plugins.shadow)
}

architectury { fabric() }

loom {
    mixin {
        defaultRefmapName.set("taczjs.refmap.json")
    }
}

val shadowBundle: Configuration by configurations.getting
val developmentFabric: Configuration by configurations.getting
configurations {
    developmentFabric.extendsFrom(common.get())
}

repositories {
    maven("https://maven.terraformersmc.com/") { name = "Terraformers" }
}

dependencies {
    modImplementation(libs.fabric.loader)

    localRuntime(libs.fabric.mixinextras)
    modLocalRuntime(libs.fabric.modmenu)

    modApi(libs.fabric.api)
    modImplementation(libs.fabric.tacz.refabricated)
    val simpleBedrockModelJar = layout.buildDirectory.file("tacz/simplebedrockmodel-fabric.jar")
    simpleBedrockModelJar.get().asFile.let { f ->
        if (f.exists()) return@let
        val taczJar = configurations.detachedConfiguration(libs.fabric.tacz.refabricated.get()).singleFile
        ZipFile(taczJar).use { zip ->
            val entry = zip.entries().asSequence()
                .first { it.name.startsWith("META-INF/jars/simplebedrockmodel") }
            f.parentFile.mkdirs()
            zip.getInputStream(entry).use { input -> f.outputStream().use { input.copyTo(it) } }
        }
    }
    modLocalRuntime(files(simpleBedrockModelJar))
    implementation(libs.commons.math3)
    minecraftLibraries(libs.commons.math3)
    implementation("com.maydaymemory:mae:${libs.versions.mae.get()}") { isTransitive = false }
    minecraftLibraries("com.maydaymemory:mae:${libs.versions.mae.get()}") { isTransitive = false }
    implementation(libs.luaj.core) { exclude(group = "org.apache.commons", module = "commons-lang3") }
    implementation(libs.luaj.jse) { exclude(group = "org.apache.commons", module = "commons-lang3") }
    implementation(libs.bcel) { exclude(group = "org.apache.commons", module = "commons-lang3") }
    modRuntimeOnly(libs.fabric.modern.keybinding)
    modImplementation(libs.fabric.cardinal.components.base)
    modImplementation(libs.fabric.cardinal.components.entity)
    modApi(libs.fabric.forgeconfigapiport)
    modImplementation(libs.fabric.kubejs)
    modLocalRuntime(libs.fabric.playeranimator)
    modLocalRuntime(libs.fabric.jei)
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