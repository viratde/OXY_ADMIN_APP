package com.oxyhotels.admin.feature_accounting.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.oxyhotels.admin.feature_accounting.presentation.states.AccountingState
import com.oxyhotels.admin.feature_auth.domain.use_cases.AuthTokenUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class AccountingViewModel @Inject constructor(
    private val authTokenUseCases: AuthTokenUseCases
) : ViewModel() {

    private val startDate: Calendar = Calendar.getInstance()
    private val endDate :Calendar = Calendar.getInstance().apply {
        add(Calendar.DATE,1)
    }
    private val dateFormatter = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
    private val _state = MutableStateFlow(AccountingState(
        startDate = dateFormatter.format(startDate.time),
        endDate = dateFormatter.format(endDate.time)
    ))
    val state = _state


    fun updateStartDate(date: String) {
        _state.update {
            state.value.copy(
                startDate = date
            )
        }
    }

    fun updateEndDate(date: String) {
        _state.update {
            state.value.copy(
                endDate = date
            )
        }
    }

    suspend fun loadData(){

    }
}