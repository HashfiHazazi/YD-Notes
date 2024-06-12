package com.ergophile.yd_notes.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <VM: ViewModel> viewModelFactoryHelper(initializer: () -> VM): ViewModelProvider.Factory{
    return object: ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return initializer() as T
        }
    }
}
