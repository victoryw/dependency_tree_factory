/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/5.2.1/userguide/tutorial_java_projects.html
 */

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building an application
    application
    idea
}

repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    // This dependency is found on compile classpath of this component and consumers.
    implementation("com.google.guava:guava:27.0.1-jre")
    implementation("org.jgrapht:jgrapht-core:1.3.0")

    implementation(platform("com.fasterxml.jackson:jackson-bom:2.9.0"))
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("org.apache.commons:commons-lang3:3.8.1")
    // Use JUnit test framework
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.1.0")
    testCompile("org.assertj:assertj-core:3.11.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.1.0")
}

tasks.named<Test>("test") {
    useJUnitPlatform();
}

application {
    // Define the main class for the application
    mainClassName = "com.victoryw.dependence.tree.story.factory.App"
}