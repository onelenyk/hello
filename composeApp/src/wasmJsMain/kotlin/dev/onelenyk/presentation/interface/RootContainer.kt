import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import dev.onelenyk.Greeting
import androidx.compose.material.Button
import androidx.compose.material.Text
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
