package com.horacek.roomtodo.util.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import org.koin.android.ext.android.getKoinScope
import org.koin.androidx.viewmodel.ViewModelStoreOwnerProducer
import org.koin.androidx.viewmodel.ext.android.getViewModelFactory
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.scope.BundleDefinition
import org.koin.androidx.viewmodel.scope.emptyState
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier


fun Fragment.argumentsToBundleDefinition(): BundleDefinition = {
    if (arguments != null)
        arguments!!
    else
        Bundle()
}


@OptIn(KoinInternalApi::class)
inline fun <reified T : ViewModel> Fragment.sharedArgumentsStateViewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition = argumentsToBundleDefinition(),
    noinline owner: ViewModelStoreOwnerProducer = { requireActivity() },
    noinline parameters: ParametersDefinition? = null,
): Lazy<T> {
    val scope = getKoinScope()
    return viewModels(ownerProducer = owner) {
        getViewModelFactory<T>(owner(), qualifier, parameters, state = state, scope = scope)
    }
}
