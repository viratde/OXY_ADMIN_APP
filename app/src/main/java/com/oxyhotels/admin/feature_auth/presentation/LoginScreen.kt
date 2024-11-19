package com.oxyhotels.admin.feature_auth.presentation


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_auth.presentation.composables.AuthButton
import com.oxyhotels.manager.feature_auth.presentation.composables.AuthHeaderText
import com.oxyhotels.admin.feature_auth.presentation.composables.AuthInput
import com.oxyhotels.admin.feature_auth.presentation.composables.AuthLabel
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel
){

    val state = loginViewModel.state.collectAsState()
    val life = LocalLifecycleOwner.current

    val snackBarHost = remember{ SnackbarHostState() }

    LaunchedEffect(key1 = state.value.isError){
        if(state.value.isError){
            println("Error Launched")
            life.lifecycleScope.launch {
                snackBarHost.showSnackbar(state.value.errorMessage)
                loginViewModel.clearMessage()
            }
        }
    }

    val onSignIn = {
        life.lifecycleScope.launch {
            withContext(Dispatchers.IO){
                loginViewModel.onSignIn()
            }
        }
    }

    val focusManager = LocalFocusManager.current

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.secondary,
        snackbarHost = { SnackbarHost(hostState = snackBarHost) },
        modifier = Modifier.imePadding().fillMaxSize()
    ) {
        Screen(
            padding = 15
        ) {

            Spacer(modifier = Modifier.weight(1f))
            AuthHeaderText(text = "Welcome to Oxyhotels ")

            Spacer(modifier = Modifier.weight(1f))

            AuthLabel(text = "Username")
            AuthInput(
                value = state.value.username,
                onValueChange = {loginViewModel.updateUsername(it)},
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Ascii
                ),
                placeholderText = "Enter Your Username"
            )

            AuthLabel(text = "Password")
            AuthInput(
                value = state.value.password,
                onValueChange = {loginViewModel.updatePassword(it)},
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSignIn()
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                placeholderText = "Enter Your Password"
            )

            Spacer(modifier = Modifier.weight(4f))

            AuthButton(
                text = "Login",
                isLoading = state.value.isLoading
            ){
                onSignIn()
            }
        }
    }

}