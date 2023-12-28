plugins {
    kotlin("jvm") version "1.9.21"
    idea
}

group = "me.alex_s168"
version = "0.1"

repositories {
    mavenCentral()
    maven {
        name = "alex's repo"
        url = uri("http://207.180.202.42:8080/libs")
        isAllowInsecureProtocol = true
    }
    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    val lwjglVersion = "3.3.1"
    val lwjglNatives = "natives-windows"
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))
    implementation("org.lwjgl:lwjgl")
    implementation("org.lwjgl:lwjgl-glfw")
    implementation("org.lwjgl:lwjgl-opengl")
    runtimeOnly("org.lwjgl:lwjgl::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-glfw::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-opengl::$lwjglNatives")

    implementation("org.l33tlabs.twl:pngdecoder:1.0")

    implementation("me.alex_s168:mathlib:0.5h4")
    implementation("me.alex_s168:meshlib:0.6")
    implementation("com.github.alex-s168:ktlib:4a380bf749")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}