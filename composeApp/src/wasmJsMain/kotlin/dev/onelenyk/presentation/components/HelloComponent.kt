package dev.onelenyk.presentation.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import dev.onelenyk.presentation.coroutineScope
import dev.onelenyk.presentation.pushCatch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import org.jetbrains.skia.Data

data class HelloState(
    val pageTitle: String = "Hello | Nazar Lenyk ",
    val title: String = "Hello",
    val name: String = "Nazar Lenyk",
    val position: String = "Android Engineer",

    val telegram: String = "https://t.me/onelenyk",
    val github: String = "https://github.com/onelenyk/",
    val linkedin: String = "https://www.linkedin.com/in/onelenyk/",
    val instagram: String = "none",
) {
    val description: AnnotatedString = buildDefaultDescription()
    val shortStory: AnnotatedString = buildDefaultShortStory()

    val uiTitle: AnnotatedString
        get() = AnnotatedString(
            text = title,
            spanStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600
            ).toSpanStyle()
        )

    private fun buildDefaultDescription() = buildAnnotatedString {
        withStyle(
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600
            ).toSpanStyle()
        ) {
            append(name)
        }
        withStyle(
            style = TextStyle(
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600
            ).toSpanStyle()
        ) {
            append(", an ")
            append(position)
        }
    }

    private fun buildDefaultShortStory() = buildAnnotatedString {
        withStyle(
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600
            ).toSpanStyle()
        ) {
            append("I do love playing with different things: ")
        }
        withStyle(
            style = TextStyle(
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600
            ).toSpanStyle()
        ) {
            append("kotlin(android, ktor, cli, kmp, tg bots), flutter, arduino-like, ffmpeg(something)")
        }
    }

}

interface HelloComponent {
    val state: StateFlow<HelloState>
}

class DefaultHelloComponent(
    componentContext: ComponentContext
) : HelloComponent, ComponentContext by componentContext {

    init {
        coroutineScope.launch {
            val translations = getHelloTranslations()

            while (isActive) {
                if (!isActive) return@launch

                for (translation in translations) {
                    _state.update {
                        it.copy(
                            title = translation
                        )
                    }
                    delay(1000L) // Show each translation for 2 seconds
                }
            }
        }
    }

    private val _state = MutableStateFlow(HelloState())
    override val state: StateFlow<HelloState>
        get() = _state.asStateFlow()

    fun getHelloTranslations(): List<String> {
        return listOf(
            "Hello",    // English
            "Привіт",   // Ukraine
            "Hola",     // Spanish
            "Bonjour",  // French
            "Hallo",    // German
            "Ciao",     // Italian
            "Olá",      // Portuguese
            "Hej",      // Swedish
            "Hallo",    // Dutch,
        )
    }
}