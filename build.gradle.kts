plugins {
    // This plugin allows you to use the 'libs' catalog
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
}