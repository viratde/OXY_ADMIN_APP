package com.oxyhotels.admin.feature_booking.presentation.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_booking.presentation.composables.DropStringInput
import com.oxyhotels.admin.feature_booking.presentation.viewmodels.QrScannerViewModel

@Composable
fun QrScannerScreen(
    navController: NavHostController,
    qrScannerViewModel: QrScannerViewModel
) {


    val context = LocalContext.current
    val state = qrScannerViewModel.state.collectAsState()

    fun showMessage(msg:String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
        qrScannerViewModel.clearMessage()
        if(msg == "Checked In Successfully" || msg == "Checked Out Successfully"){
            navController.popBackStack()
        }
    }

    val scanner = remember {
        GmsBarcodeScanning.getClient(context,qrScannerViewModel.options)
    }
    val moduleInstallClient = remember {
        ModuleInstall.getClient(context as Activity)
    }

    var code by rememberSaveable() {
        mutableStateOf("")
    }


    LaunchedEffect(Unit){
        qrScannerViewModel.onScan(
            moduleInstallClient = moduleInstallClient,
            scanner = scanner,
            context = context
        )
    }


    LaunchedEffect(key1 = state.value.isError ){
        if(state.value.isError){
            showMessage(state.value.errorMessage)
        }
    }



    Screen(
        isScrollable = false,
        padding = 20,
        verticalArrangement = Arrangement.Center
    ) {

        if(state.value.isInstalling || state.value.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                color = Color.White
            )
        }else if(state.value.bookingId != null){
            Text(
                text = state.value.bookingId.toString(),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }else{
            Column(
                modifier = Modifier.widthIn(max = 320.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                DropStringInput(
                    placeholder = "Enter Code",
                    value = code,
                    onValueChange = {code = it}
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { qrScannerViewModel.scan(context = context) }) {
                        Text(
                            text = "Scan Again",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                    }

                    Button(onClick = {
                        qrScannerViewModel.checkInByCode(code)
                    }) {
                        Text(
                            text = "Submit Code",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                    }
                }
            }
        }
    }
}