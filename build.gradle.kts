plugins {
    id("org.springframework.boot") version "4.0.5"
    id("io.spring.dependency-management") version "1.1.6"
}

allprojects {
    group = "de.sp.taskmanager"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}