package me.androiddev.ui_bonus.di

import android.content.Context
import com.mobile.core.di.viewmodel.ViewModelFactoryBuilder
import dagger.Component
import me.androiddev.core.App.Companion.coreComponent
import me.androiddev.core.di.CoreComponent
import me.androiddev.core.di.UIScope
import me.androiddev.data.di.InterceptorModule
import me.androiddev.data.di.NetworkModule
import me.androiddev.data.di.RepositoryModule
import me.androiddev.ui_bonus.FragmentBonus
import me.androiddev.ui_bonus.FragmentSplash


@UIScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [ViewModelFactoryBuilder::class, NetworkModule::class, InterceptorModule::class, RepositoryModule::class]
)
interface UIComponent {

    companion object {
        private var uiComponent: UIComponent? = null
        fun getComponent(context: Context): UIComponent {
            if (uiComponent == null)
                uiComponent = DaggerUIComponent.builder()
                    .coreComponent(coreComponent(context))
                    .build()
            return uiComponent!!
        }
    }

    fun inject(fragment: FragmentSplash)
    fun inject(fragment: FragmentBonus)
}