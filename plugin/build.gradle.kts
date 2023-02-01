/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Gradle plugin project to get you started.
 * For more details take a look at the Writing Custom Plugins chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.6/userguide/custom_plugins.html
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    // Apply the Java Gradle plugin development plugin to add support for developing Gradle plugins
    `java-gradle-plugin`

    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.7.10"

    `maven-publish`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest("1.7.10")

            dependencies {
                // Use newer version of JUnit Engine for Kotlin Test
                implementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")
            }
        }

        // Create a new test suite
        val functionalTest by registering(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest("1.7.10")

            dependencies {
                // functionalTest test suite depends on the production code in tests
                implementation(project())

                // Use newer version of JUnit Engine for Kotlin Test
                implementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")
                implementation("org.assertj:assertj-core:3.24.2")
            }

            targets {
                all {
                    // This test suite should run after the built-in test suite has run its tests
                    testTask.configure { shouldRunAfter(test) }
                }
            }
        }
    }
}

gradlePlugin {
    // Define the plugin
    plugins {
        create("countLines") {
            id = "com.poisonedyouth.countLines"
            implementationClass = "com.poisonedyouth.GradleCustomPlugin"
            version = "1.0.0"
            description = "Count project lines of specified file type"
            displayName = "Count project lines"
        }
    }
}

gradlePlugin.testSourceSets(sourceSets["functionalTest"])

tasks.named<Task>("check") {
    // Include functionalTest as part of the check lifecycle
    dependsOn(testing.suites.named("functionalTest"))
}
