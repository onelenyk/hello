package dev.onelenyk

import App
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val appElement = document.getElementById("app") ?: error("App element not found")

    ComposeViewport(appElement) {
        App()
    }
}