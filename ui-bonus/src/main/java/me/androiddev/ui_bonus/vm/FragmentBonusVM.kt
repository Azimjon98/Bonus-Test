package me.androiddev.ui_bonus.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.core.util.Connectivity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import me.androiddev.core.mvvm.*
import me.androiddev.data.model.BonusModel
import me.androiddev.data.model.onFailure
import me.androiddev.data.model.onSuccess
import me.androiddev.data.usecase.GetBonusUseCase
import me.androiddev.logintestapp.util.CoroutineContextProvider
import javax.inject.Inject

class FragmentBonusVM @Inject constructor(
    private val connectivity: Connectivity,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val getBonusUseCase: GetBonusUseCase
) : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        bonusEvent.postValue(Error(throwable))
    }

    var accessToken: String = ""
        set(value) {
            if (field != value) {
                field = value
                fetchBonus()
            }
        }

    var model = ObservableField<BonusModel>()

    var bonusLoaded = false
    var bonusEvent = MutableLiveData<UIState<BonusModel>>()

    fun fetchBonus() =
        viewModelScope.launch(coroutineContextProvider.io + errorHandler) {
            if (connectivity.hasNetworkAccess().not()) {
                bonusEvent.postValue(NoInternetState())
                return@launch
            }

            bonusEvent.postValue(Loading())
            getBonusUseCase.invoke(accessToken)
                .onSuccess {
                    if (it.apiResult.status == 0)
                        bonusEvent.postValue(Success(it.data.mapToDomainModel()))
                    else
                        bonusEvent.postValue(Error(Throwable(it.apiResult.message)))
                }.onFailure {
                    bonusEvent.postValue(Error(it.throwable))
                }

        }

}