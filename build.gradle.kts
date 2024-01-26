
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.android) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.ktlint.gradle) apply false
    alias(libs.plugins.googlemaps.platform) apply false
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

    val ktlintVersion = rootProject.libs.versions.ktlintVersion.get()

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

afterEvaluate {
    // Install KtLint check Git hook
    tasks.named("clean") {
        dependsOn(":installGitHooks")
    }
}
