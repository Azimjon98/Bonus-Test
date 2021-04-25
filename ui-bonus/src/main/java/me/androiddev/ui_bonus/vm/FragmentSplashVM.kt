package me.androiddev.ui_bonus.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.core.util.Connectivity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.androiddev.core.common.Constants
import me.androiddev.core.mvvm.*
import me.androiddev.data.model.onFailure
import me.androiddev.data.model.onSuccess
import me.androiddev.data.model.request.AccessTokenRequest
import me.androiddev.data.usecase.GetAccessTokenUseCase
import me.androiddev.logintestapp.util.CoroutineContextProvider
import javax.inject.Inject

class FragmentSplashVM @Inject constructor(
    private val connectivity: Connectivity,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        tokenEvent.postValue(Error(throwable))
    }

    var accessTokenLoaded = false
    var tokenEvent = LiveEvent<UIState<String>>()

    fun fetchAccessToken() =
        viewModelScope.launch(coroutineContextProvider.io + errorHandler) {
            if (connectivity.hasNetworkAccess().not()) {
                tokenEvent.postValue(NoInternetState())
                return@launch
            }

            tokenEvent.postValue(Loading())
            getAccessTokenUseCase.invoke(
                AccessTokenRequest(
                    Constants.ClientID, "", "device", Constants.DeviceID, 0, 0, 0
                )
            ).onSuccess {
                delay(3000)
                if (it.apiResult.status == 0)
                    tokenEvent.postValue(Success(it.accessToken))
                else
                    tokenEvent.postValue(Error(Throwable(it.apiResult.message)))
            }.onFailure {
                tokenEvent.postValue(Error(it.throwable))
            }

        }
}