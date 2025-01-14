import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import dev.onelenyk.presentation.components.RootComponent


@Composable
fun RootContainer(component: RootComponent) {
    Children(
        modifier = Modifier
            .fillMaxSize(),
        stack = component.menuNavigationState,
    ) {
        when (val currentScreen = it.instance) {
            is RootComponent.RootEntry.Main -> {
                MainScreen(component)
            }
            is RootComponent.RootEntry.Lenyk -> {
                LenykScreen(component)
            }
            is RootComponent.RootEntry.Hello -> {
                HelloScreen(currentScreen.component)
            }

        }
    }

    return
}
