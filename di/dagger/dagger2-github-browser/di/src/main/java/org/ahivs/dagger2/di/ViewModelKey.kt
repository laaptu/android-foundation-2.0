package org.ahivs.dagger2.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
annotation class ViewModelKey(val classKey: KClass<out ViewModel>)