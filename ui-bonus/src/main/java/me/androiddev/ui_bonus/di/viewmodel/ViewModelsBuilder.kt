package com.mobile.core.di.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.androiddev.ui_bonus.vm.FragmentBonusVM
import me.androiddev.ui_bonus.vm.FragmentSplashVM

@Module
abstract class ViewModelsBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(FragmentSplashVM::class)
    abstract fun bindFragmentSplashVM(vm: FragmentSplashVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FragmentBonusVM::class)
    abstract fun bindFragmentBonusVM(vm: FragmentBonusVM): ViewModel

}