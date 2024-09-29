
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import dev.onelenyk.presentation.components.DefaultRootComponent
import dev.onelenyk.presentation.components.RootComponent

@Composable
fun App() {
    val lifecycle = LifecycleRegistry()

    val rootComponent: RootComponent = DefaultRootComponent(
        DefaultComponentContext(lifecycle)
    )
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            RootContainer(rootComponent)
        }
    }
}