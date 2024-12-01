package ru.kram.sandbox.build.plugins

import com.android.tools.r8.internal.VK
import com.vk.gradle.plugin.compose.utils.VkomposeExtension
import com.vk.gradle.plugin.compose.utils.VkomposePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import ru.kram.sandbox.build.utils.vkomposePlugin

class VkomposePlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        target.pluginManager.apply(vkomposePlugin)

        target.extensions.configure<VkomposeExtension> {
            skippabilityCheck {
                stabilityConfigurationPath = "/path/file.config"
                isEnabled = false
            }

            recompose {
                isHighlighterEnabled = true
                isLoggerEnabled = true

                logger {
                    logModifierChanges = true // true by default since 0.5
                    logFunctionChanges = true // true by default since 0.5. log when function arguments (like lambdas or function references) of composable function are changed
                }
            }

            testTag {
                isApplierEnabled = true
                isDrawerEnabled = false
                isCleanerEnabled = false
            }

            sourceInformationClean = true
        }
    }
}