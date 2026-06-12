package com.example.swrunevault.compose

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner

class OverlayComposeOwner :
    LifecycleOwner,
    SavedStateRegistryOwner,
    ViewModelStoreOwner {

    private val lifecycleRegistry =
        LifecycleRegistry(this)

    private val internalViewModelStore =
        ViewModelStore()

    private val savedStateRegistryController =
        SavedStateRegistryController.create(this)

    init {

        savedStateRegistryController.performAttach()

        savedStateRegistryController.performRestore(null)

        lifecycleRegistry.currentState =
            Lifecycle.State.CREATED

        lifecycleRegistry.currentState =
            Lifecycle.State.STARTED

        lifecycleRegistry.currentState =
            Lifecycle.State.RESUMED
    }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    override val viewModelStore: ViewModelStore
        get() = internalViewModelStore

    fun destroy() {

        lifecycleRegistry.currentState =
            Lifecycle.State.DESTROYED

        internalViewModelStore.clear()
    }
}