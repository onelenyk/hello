package dev.onelenyk.presentation.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import dev.onelenyk.presentation.coroutineScope
import dev.onelenyk.presentation.pushCatch
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import org.jetbrains.skia.Data

interface RootComponent {
    val menuNavigationState: Value<ChildStack<RootConfig, RootEntry>>


    sealed class RootEntry {
        data class Main(val title:String) : RootEntry()
        data class Lenyk(val timeStamp: Long) : RootEntry()
    }

    @Serializable
    sealed class RootConfig {
        @Serializable
        data object Main : RootConfig()
        data object Lenyk : RootConfig()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val menuNavigation = StackNavigation<RootComponent.RootConfig>()
    private val _menuNavigationState: Value<ChildStack<RootComponent.RootConfig, RootComponent.RootEntry>> =
        childStack(
            source = menuNavigation,
            serializer = RootComponent.RootConfig.serializer(),
            initialConfiguration = RootComponent.RootConfig.Main,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    override val menuNavigationState: Value<ChildStack<RootComponent.RootConfig, RootComponent.RootEntry>> =
        _menuNavigationState

    private fun createChild(config: RootComponent.RootConfig, context: ComponentContext): RootComponent.RootEntry {
        return when(config){
            RootComponent.RootConfig.Lenyk -> RootComponent.RootEntry.Lenyk(Clock.System.now().epochSeconds)
            RootComponent.RootConfig.Main -> RootComponent.RootEntry.Main("")
        }
    }

    init {
        coroutineScope.launch {
            delay(10000L)
            menuNavigation.pushCatch(RootComponent.RootConfig.Lenyk)
        }
    }
}