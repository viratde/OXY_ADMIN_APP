package com.oxyhotels.admin.feature_manager.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxyhotels.admin.feature_auth.domain.use_cases.AuthTokenUseCases
import com.oxyhotels.admin.feature_manager.domain.models.Manager
import com.oxyhotels.admin.feature_manager.domain.models.ManagerException
import com.oxyhotels.admin.feature_manager.domain.models.ManagerResponse
import com.oxyhotels.admin.feature_manager.domain.use_cases.ManagerUseCases
import com.oxyhotels.admin.feature_manager.presentation.states.ManagersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class AllManagersViewmodel @Inject constructor(
    private val managerUseCases: ManagerUseCases,
    private val authUseCases: AuthTokenUseCases,
) : ViewModel() {


    private val _state = MutableStateFlow(ManagersState())

    val state = _state


    init {
        loadManagers()
    }


    fun updateManager(
        manager: Manager
    ) {

        if (state.value.isManagerUpdating) {
            return
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val token = authUseCases.getTokenUseCases()

                if (token == null) {
                    _state.update {
                        state.value.copy(
                            isError = true,
                            errorMessage = "Please try after some time."
                        )
                    }
                    return@withContext
                }

                _state.update {
                    state.value.copy(
                        isManagerUpdating = true
                    )
                }

                performAction(
                    actionTaker = {
                        managerUseCases.updateManagerUseCases(
                            token,
                            manager = manager
                        )
                    },
                    onSuccess = { state, data ->
                        state.copy(
                            isManagerUpdating = false,
                            managers = state.managers.map {
                                if (it._id == data._id) {
                                    data
                                } else {
                                    it
                                }
                            }
                        )
                    },
                    onError = {
                        it.copy(
                            isManagerUpdating = false
                        )
                    },
                    successMessage = "Manager Updated Successfully"
                )


            }
        }

    }

    private fun loadManagers(
    ) {

        if (state.value.isManagersLoading) {
            return
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val token = authUseCases.getTokenUseCases()

                if (token == null) {
                    _state.update {
                        state.value.copy(
                            isError = true,
                            errorMessage = "Please try after some time."
                        )
                    }
                    return@withContext
                }

                _state.update {
                    state.value.copy(
                        isManagersLoading = true,
                        isManagersLoaded = false
                    )
                }

                performAction(
                    actionTaker = {
                        managerUseCases.listAllManagerUseCases(
                            token
                        )
                    },
                    onSuccess = { state, data ->
                        state.copy(
                            isManagersLoaded = true,
                            isManagersLoading = false,
                            managers = data
                        )
                    },
                    onError = {
                        it.copy(
                            isManagersLoading = false
                        )
                    },
                    successMessage = "Managers Updated Successfully"
                )

            }
        }

    }

    private fun <T> performAction(
        actionTaker: suspend () -> ManagerResponse<T>,
        successMessage: String,
        onError: (ManagersState) -> ManagersState,
        onSuccess: (ManagersState, T) -> ManagersState
    ) {
        viewModelScope.launch {

            try {
                val response = actionTaker()

                if (!response.status || response.data == null) {
                    _state.update {
                        onError(
                            state.value.copy(
                                isError = true,
                                errorMessage = response.message
                            )
                        )
                    }
                } else {
                    _state.update {
                        onSuccess(
                            state.value.copy(
                                isError = true,
                                errorMessage = successMessage,
                            ),
                            response.data
                        )
                    }

                }

            } catch (err: ManagerException) {
                err.printStackTrace()
                _state.update {
                    onError(
                        state.value.copy(
                            isError = true,
                            errorMessage = err.message
                        )
                    )
                }
            } catch (err: Exception) {
                err.printStackTrace()
                _state.update {
                    onError(
                        state.value.copy(
                            isError = true,
                            errorMessage = "Please try after some time"
                        )
                    )
                }
            }

        }

    }

    fun clearMessage() {
        _state.update {
            state.value.copy(
                isError = false,
                errorMessage = ""
            )
        }
    }

}