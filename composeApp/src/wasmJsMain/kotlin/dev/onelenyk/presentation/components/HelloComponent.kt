package dev.onelenyk.presentation.components

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

data class HelloState(val title: String = "Hello")

interface HelloComponent {
    val state: StateFlow<HelloState>
}

class DefaultHelloComponent(
    componentContext: ComponentContext
) : HelloComponent, ComponentContext by componentContext {

    init {
        coroutineScope.launch {
            val translations = getHelloTranslations()

            while (isActive){
                if(!isActive)return@launch

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
            "Hallo" ,    // Dutch,
            "\uD83D\uDC7E \uD83D\uDC7E \uD83D\uDC7E"
        )
    }
}