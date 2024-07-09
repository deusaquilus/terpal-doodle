plugins {
    id ("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("io.exoquery.terpal-plugin") version "1.9.22-0.2.0"
    application
}

version = "1.0.0"
group   = "com.my.cool.app"

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    mavenLocal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    target.compilations.all {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    val doodleVersion = "0.10.2" // <--- Latest Doodle version

    dependencies {
        val osName = System.getProperty("os.name")
        val targetOs = when {
            osName == "Mac OS X"       -> "macos"
            osName.startsWith("Win"  ) -> "windows"
            osName.startsWith("Linux") -> "linux"
            else                       -> error("Unsupported OS: $osName")
        }

        val osArch = System.getProperty("os.arch")
        val targetArch = when (osArch) {
            "x86_64", "amd64" -> "x64"
            "aarch64"         -> "arm64"
            else              -> error("Unsupported arch: $osArch")
        }

        val target = "$targetOs-$targetArch"

        implementation("io.nacular.doodle:core:$doodleVersion"               )
        //implementation("io.nacular.doodle:desktop-jvm-$target:$doodleVersion") // Desktop apps are tied to specific platforms
        implementation("io.nacular.doodle:desktop-jvm-linux-x64:$doodleVersion@pom") {
            isTransitive = true
        }
        //implementation("io.nacular.doodle:desktop-jvm:$doodleVersion")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1") {
            version {
                strictly("1.8.1")
            }
        }
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.8.1") {
            version {
                strictly("1.8.1")
            }
        }


        // Optional
        implementation ("io.nacular.doodle:controls:$doodleVersion" )
        implementation ("io.nacular.doodle:animation:$doodleVersion")
        implementation ("io.nacular.doodle:themes:$doodleVersion"   )
    }
}

application {
  mainClass.set("io.deusaquilus.DoodleExampleKt")
}