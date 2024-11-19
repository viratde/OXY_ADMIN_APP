package com.oxyhotels.admin.feature_location.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxyhotels.admin.feature_auth.domain.use_cases.AuthTokenUseCases
import com.oxyhotels.admin.feature_location.domain.models.LocationData
import com.oxyhotels.admin.feature_location.domain.use_cases.LocationUseCases
import com.oxyhotels.admin.feature_location.presentation.states.LocationsState
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
class LocationViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases,
    private val authUseCases: AuthTokenUseCases,
) : ViewModel() {


    private val _state = MutableStateFlow(LocationsState())

    val state = _state


    init {
        loadManagers()
    }


    fun updateManager(
        locationData: LocationData
    ) {

        if (state.value.isLocationUpdating) {
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
                        isLocationUpdating = true
                    )
                }

                performAction(
                    actionTaker = {
                        locationUseCases.updateLocationUseCases(
                            token,
                            locationData = locationData
                        )
                    },
                    onSuccess = { state, data ->
                        state.copy(
                            isLocationUpdating = false,
                            locations = if (
                                state.locations.find {
                                    it._id == data._id
                                } != null
                            ) state.locations.map {
                                if (it._id == data._id) {
                                    data
                                } else {
                                    it
                                }
                            } else state.locations + data
                        )
                    },
                    onError = {
                        it.copy(
                            isLocationUpdating = false
                        )
                    },
                    successMessage = "Locations Updated Successfully"
                )


            }
        }

    }

    private fun loadManagers(
    ) {

        if (state.value.isLocationsLoading) {
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
                        isLocationsLoading = true,
                        isLocationsLoaded = false
                    )
                }

                performAction(
                    actionTaker = {
                        locationUseCases.listAllLocationsUseCases(
                            token
                        )
                    },
                    onSuccess = { state, data ->
                        state.copy(
                            isLocationsLoaded = true,
                            isLocationsLoading = false,
                            locations = data
                        )
                    },
                    onError = {
                        it.copy(
                            isLocationsLoading = false
                        )
                    },
                    successMessage = "Locations Updated Successfully"
                )

            }
        }

    }

    private fun <T> performAction(
        actionTaker: suspend () -> ManagerResponse<T>,
        successMessage: String,
        onError: (LocationsState) -> LocationsState,
        onSuccess: (LocationsState, T) -> LocationsState
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