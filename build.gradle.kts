import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.libsDirectory
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.21-1.0.16" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("com.diffplug.spotless") version "6.25.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0" apply false
}

apply(from = "buildscripts/git-hooks.gradle")

allprojects {

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            )
        }
    }

    val ktlintVersion = "1.0.1"

    apply<org.jlleitschuh.gradle.ktlint.KtlintPlugin>()

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set(ktlintVersion)
        android.set(true)
        verbose.set(true)
    }

    apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
    // Spotless Configuration
    val configureSpotless: SpotlessExtension.() -> Unit = {
        kotlin {
            ktlint(ktlintVersion)
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
        }
        kotlinGradle {
            ktlint(ktlintVersion)
            target("**/*.kts")
            targetExclude("**/build/**/*.kts")
        }
        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml")
        }
    }

    if (project === rootProject) {
        extensions.configure<SpotlessExtension> { predeclareDeps() }
        extensions.configure<SpotlessExtensionPredeclare>(configureSpotless)
    } else {
        extensions.configure(configureSpotless)
    }
}