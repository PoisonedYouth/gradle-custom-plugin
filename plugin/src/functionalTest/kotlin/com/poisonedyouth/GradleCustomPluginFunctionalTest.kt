/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.poisonedyouth

import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.writeText

/**
 * A simple functional test for the 'com.poisonedyouth.countLines' plugin.
 */
class GradleCustomPluginFunctionalTest {

    @field:TempDir
    lateinit var projectDir: Path

    private val buildFile by lazy { projectDir.resolve("build.gradle") }
    private val settingsFile by lazy { projectDir.resolve("settings.gradle") }

    @Test
    fun `returns correct report for single file type`() {
        // Set up the test build
        settingsFile.writeText("")
        buildFile.writeText(
            """
            plugins {
                id('com.poisonedyouth.countLines')
            }
        """.trimIndent()
        )

        // creates folders in the temp project
        val testClassLocation = projectDir.resolve("src/main/kotlin").resolve("TestClass.kt")
        Files.createDirectories(testClassLocation.parent)

        this::class.java.classLoader
            .getResource("TestClass.kt")!!.file.let(::File)
            .copyTo(testClassLocation.toFile())

        // Run the build
        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("printReportToFile")
        runner.withProjectDir(projectDir.toFile())
        val result = runner.build()

        // Verify the result
        assertThat(
            result.output
        ).contains(
            "Finished producing count lines report in path: build/reports/countLines.txt"
        )
        assertThat(
            Files.readAllLines((projectDir.resolve("build/reports/countLines.txt")))
        ).contains(
            "Filetype:kt \t-\t Lines:6"
        )
    }

    @Test
    fun `returns correct report for multiple file types`() {
        // Set up the test build
        settingsFile.writeText("")
        buildFile.writeText(
            """
            plugins {
                id('com.poisonedyouth.countLines')
            }
            tasks.printReportToFile{
                fileTypes = ["kt", "yaml"]
            }
        """.trimIndent()
        )

        // creates folders in the temp project
        val testClassLocation = projectDir.resolve("src/main/kotlin").resolve("TestClass.kt")
        Files.createDirectories(testClassLocation.parent)

        this::class.java.classLoader
            .getResource("TestClass.kt")!!.file.let(::File)
            .copyTo(testClassLocation.toFile())

        val testConfigLocation = projectDir.resolve("src/main/kotlin").resolve("Config.yaml")

        this::class.java.classLoader
            .getResource("Config.yaml")!!.file.let(::File)
            .copyTo(testConfigLocation.toFile())

        // Run the build
        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("printReportToFile")
        runner.withProjectDir(projectDir.toFile())
        val result = runner.build()

        // Verify the result
        assertThat(
            result.output
        ).contains(
            "Finished producing count lines report in path: build/reports/countLines.txt"
        )
        assertThat(
            Files.readAllLines((projectDir.resolve("build/reports/countLines.txt"))),
        ).containsAll(listOf("Filetype:kt \t-\t Lines:6", "Filetype:yaml \t-\t Lines:4"))
    }
}
