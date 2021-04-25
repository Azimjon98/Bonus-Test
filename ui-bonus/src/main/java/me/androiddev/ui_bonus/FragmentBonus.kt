package me.androiddev.ui_bonus

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import me.androiddev.core.common.hide
import me.androiddev.core.common.show
import me.androiddev.core.common.showAlertDialog
import me.androiddev.core.mvvm.Error
import me.androiddev.core.mvvm.Loading
import me.androiddev.core.mvvm.NoInternetState
import me.androiddev.core.mvvm.Success
import me.androiddev.ui_bonus.databinding.WindowBonusBinding
import me.androiddev.ui_bonus.di.UIComponent
import me.androiddev.ui_bonus.vm.FragmentBonusVM
import javax.inject.Inject

class FragmentBonus(val layoutId: Int = R.layout.window_bonus) : Fragment() {

    companion object {
        const val KEY_ACCESS_TOKEN = "token"
    }

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FragmentBonusVM

    lateinit var binding: WindowBonusBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        UIComponent.getComponent(requireContext()).inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, modelFactory).get(FragmentBonusVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WindowBonusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        arguments?.let {
            if (it.containsKey(KEY_ACCESS_TOKEN)) {
                viewModel.accessToken = it.getString(KEY_ACCESS_TOKEN, "")
            }
        }

        initVM()
    }


    private fun initVM() {
        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Boolean>("connection")
            ?.observe(viewLifecycleOwner) {
                if (viewModel.bonusLoaded.not())
                    viewModel.fetchBonus()
            }

        viewModel.bonusEvent.observe(viewLifecycleOwner) {
            binding.progress.hide()

            when (it) {
                is Loading -> {
                    binding.progress.show()
                }
                is Success -> {
                    viewModel.model.set(it.data)
                    binding.bonusCard.postDelayed({
                        binding.bonusCard.show()
                    }, 50)
                    viewModel.bonusLoaded = true
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

}