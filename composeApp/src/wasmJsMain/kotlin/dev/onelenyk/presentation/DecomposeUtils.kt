package dev.onelenyk.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigator
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

// val ComponentContext.coroutineScope: CoroutineScope
//    get() = coroutineScope(Dispatchers.Main + SupervisorJob())

val ComponentContext.coroutineScope: CoroutineScope
    get() = cancellableCoroutineScope(Dispatchers.Main + SupervisorJob())

fun <C : Any> StackNavigator<C>.pushCatch(configuration: C, onComplete: () -> Unit = {}) {
    executeSafely {
        push(configuration, onComplete)
    }
}

fun executeSafely(op: () -> Unit) {
    return try {
        op()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun LifecycleOwner.cancellableCoroutineScope(
    context: CoroutineContext = Dispatchers.Main.immediate,
): CoroutineScope =
    CoroutineScope(context = context).withLifecycle(lifecycle)

fun CoroutineScope.withLifecycle(lifecycle: Lifecycle): CoroutineScope {
    lifecycle.doOnDestroy {
        cancelScopeAsync(this)
    }
    return this
}

fun cancelScopeAsync(cancellationScope: CoroutineScope) {
    CoroutineScope(Dispatchers.Main).launch(block = {
        cancellationScope.cancel()
    })
}
