import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.api.tasks.Delete
import org.springframework.boot.gradle.tasks.run.BootRun

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.1.0.RELEASE")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.0")
        classpath("org.jetbrains.kotlin:kotlin-allopen:1.3.0")
    }
}

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.0"
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.0"
    id("org.springframework.boot") version "2.1.0.RELEASE"
}

repositories {
    jcenter()
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.0")
    compile("org.jetbrains.kotlin:kotlin-reflect:1.3.0")
    compile("org.jetbrains.kotlinx:kotlinx-support-jdk8:0.3")

    compile("org.springframework.boot:spring-boot:2.1.0.RELEASE")
    compile("org.springframework.boot:spring-boot-autoconfigure:2.1.0.RELEASE")
    compile("org.eclipse.jetty:jetty-server:9.4.8.v20180619")
    compile("org.eclipse.jetty:jetty-servlet:9.4.8.v20180619")
    compile("org.eclipse.jetty:jetty-webapp:9.4.8.v20180619")
    compile("org.springframework:spring-webmvc:5.1.2.RELEASE")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.4")

    compile("mysql:mysql-connector-java:6.0.6")
    compile("com.zaxxer:HikariCP:2.7.6")
}

task<Copy>("copyCertificatesKeystore") {
    tasks["bootJar"].dependsOn(this)

    from(rootDir) { include("cacerts") }
    into("$buildDir/resources/main")
}
