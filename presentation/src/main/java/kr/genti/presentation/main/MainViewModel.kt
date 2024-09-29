package kr.genti.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kr.genti.core.state.UiState
import kr.genti.domain.entity.response.GenerateStatusModel
import kr.genti.domain.enums.GenerateStatus
import kr.genti.domain.repository.GenerateRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val generateRepository: GenerateRepository,
    ) : ViewModel() {
        private val _getStatusResult = MutableSharedFlow<Boolean>()
        val getStatusResult: SharedFlow<Boolean> = _getStatusResult

        private val _postResetResult = MutableSharedFlow<Boolean>()
        val postResetResult: SharedFlow<Boolean> = _postResetResult

        private val _notificationState = MutableStateFlow(GenerateStatus.EMPTY)
        val notificationState: StateFlow<GenerateStatus> = _notificationState

        private val _userVerifyState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
        val userVerifyState: StateFlow<UiState<Boolean>> = _userVerifyState

        var currentStatus: GenerateStatus = GenerateStatus.NEW_REQUEST_AVAILABLE
        lateinit var newPicture: GenerateStatusModel

        fun getGenerateStatusFromServer(isNotification: Boolean) {
            viewModelScope.launch {
                generateRepository
                    .getGenerateStatus()
                    .onSuccess {
                        currentStatus = it.status
                        newPicture = it
                        if (isNotification) {
                            _notificationState.value = it.status
                        }
                    }.onFailure {
                        _getStatusResult.emit(false)
                    }
            }
        }

        fun resetNotificationState() {
            _notificationState.value = GenerateStatus.EMPTY
        }

        fun postResetStateToServer() {
            viewModelScope.launch {
                generateRepository
                    .getCanceledToReset(
                        newPicture.pictureGenerateRequestId.toString(),
                    ).onSuccess {
                        _postResetResult.emit(true)
                        getGenerateStatusFromServer(false)
                    }.onFailure {
                        _postResetResult.emit(false)
                    }
            }
        }

        fun checkNewPictureInitialized() = ::newPicture.isInitialized

        fun getIsUserVerifiedFromServer() {
            _userVerifyState.value = UiState.Loading
            viewModelScope.launch {
                generateRepository
                    .getIsUserVerified()
                    .onSuccess {
                        _userVerifyState.value = UiState.Success(it)
                    }.onFailure {
                        _userVerifyState.value = UiState.Failure(it.message.orEmpty())
                    }
            }
        }
    }
