package me.androiddev.ui_bonus

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import me.androiddev.core.common.showAlertDialog
import me.androiddev.core.mvvm.Error
import me.androiddev.core.mvvm.Loading
import me.androiddev.core.mvvm.NoInternetState
import me.androiddev.core.mvvm.Success
import me.androiddev.ui_bonus.FragmentBonus.Companion.KEY_ACCESS_TOKEN
import me.androiddev.ui_bonus.di.UIComponent
import me.androiddev.ui_bonus.vm.FragmentSplashVM
import javax.inject.Inject

class FragmentSplash(val layoutId: Int = R.layout.window_splash) : Fragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FragmentSplashVM

    override fun onAttach(context: Context) {
        super.onAttach(context)
        UIComponent.getComponent(requireContext()).inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, modelFactory).get(FragmentSplashVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVM()
    }

    private fun initVM() {
        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Boolean>("connection")
            ?.observe(viewLifecycleOwner) {
                if (viewModel.accessTokenLoaded.not())
                    viewModel.fetchAccessToken()
            }



        viewModel.tokenEvent.observe(this) {
            when (it) {
                is Loading -> {
                }
                is Success -> {
                    viewModel.accessTokenLoaded = true
                    findNavController().navigate(
                        R.id.action_fragmentSplash_to_bonusFragment,
                        bundleOf(KEY_ACCESS_TOKEN to it.data)
                    )
                }
                is Error -> {
                    it.error.message?.let { requireContext().showAlertDialog(it) }
                }
                is NoInternetState -> {
                    requireContext().showAlertDialog(getString(R.string.no_connection))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchAccessToken()
    }
}