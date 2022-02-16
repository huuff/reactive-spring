import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "xyz.haff"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.wrapper {
    version = "7.4"
}

val reactorVersion = "3.4.15"
dependencies {
    implementation("io.projectreactor:reactor-core:$reactorVersion")
    implementation("io.projectreactor.addons:reactor-adapter:3.4.6")
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("io.projectreactor:reactor-test:$reactorVersion")
    testImplementation(kotlin("test"))

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}