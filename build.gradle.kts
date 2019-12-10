plugins {
    java
    scala
    application
}

group = "com.github.hzqd"
version = "0.1"

application {
    mainClassName = "App"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:2.13.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
