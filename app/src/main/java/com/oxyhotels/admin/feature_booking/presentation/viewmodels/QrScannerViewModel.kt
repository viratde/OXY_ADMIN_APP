package com.oxyhotels.admin.feature_booking.presentation.viewmodels

import android.app.Activity
import android.content.Context
import android.print.PrintJobInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.moduleinstall.InstallStatusListener
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate
import com.google.gson.Gson
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.oxyhotels.admin.common.util.Response
import com.oxyhotels.admin.constant.Constant
import com.oxyhotels.admin.feature_auth.domain.use_cases.AuthTokenUseCases
import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import com.oxyhotels.admin.feature_booking.domain.use_cases.BookingUseCases
import com.oxyhotels.admin.feature_booking.presentation.states.QrScannerState
import com.oxyhotels.admin.feature_booking.presentation.utils.BookingIdClass
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.HotelUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

@HiltViewModel
class QrScannerViewModel @Inject constructor(
    private val authTokenUseCases: AuthTokenUseCases,
    private val hotelUseCases: HotelUseCases,
    private val bookingUseCases: BookingUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(
        QrScannerState(
            bookingModel = null
        )
    )

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())

    init {
        dateFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
    }

    fun checkExistence(bookingId: Int): Boolean {
        return runBlocking {
            return@runBlocking withContext(Dispatchers.IO) {
                val bookingModel = bookingUseCases.getBookingByBookingId(bookingId = bookingId)
                return@withContext bookingModel == null
            }
        }
    }

    suspend fun getBookingByBookingId(
        bookingId: Int
    ) {
        _state.update {
            state.value.copy(
                isLoading = true
            )
        }

        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("Text", "Plain"))
            }
        }
        try {

            val token = authTokenUseCases.getTokenUseCases()
            val httpResponse = client.post(Constant.getBookingByBookingIdRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                    parameter("bookingId", bookingId)
                }
            }
            if (httpResponse.status != HttpStatusCode.OK) {
                _state.update {
                    state.value.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = "Please try after some time"
                    )
                }
                return
            }
            val rData = Gson().fromJson(httpResponse.body<String>(), Response::class.java)
            if (!rData.status) {
                _state.update {
                    state.value.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = rData.message
                    )
                }
                return
            }
            val booking = Gson().fromJson(rData.data, BookingModel::class.java)
            bookingUseCases.addBookingUseCases(listOf(booking))
            _state.update {
                state.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Booking Found."
                )
            }

        } catch (err: Exception) {
            err.printStackTrace()
            _state.update {
                state.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Booking Not Found"
                )
            }
        }
    }


    val state = _state

    val options =
        GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC
            )
            .build()


    fun releaseModules(moduleInstallClient: ModuleInstallClient, scanner: GmsBarcodeScanner) {
        moduleInstallClient.releaseModules(scanner)
    }

    fun onScan(
        moduleInstallClient: ModuleInstallClient,
        scanner: GmsBarcodeScanner,
        context: Context
    ) {
        _state.update {
            state.value.copy(
                isLoading = true
            )
        }
        moduleInstallClient.releaseModules(scanner)
        moduleInstallClient
            .areModulesAvailable(scanner)
            .addOnSuccessListener {
                if (it.areModulesAvailable()) {
                    _state.update {
                        state.value.copy(
                            isLoading = false
                        )
                    }
                    scan(context = context)
                } else {

                    val listener = object : InstallStatusListener {
                        override fun onInstallStatusUpdated(update: ModuleInstallStatusUpdate) {
                            update.progressInfo?.let { d ->
                                val progress =
                                    (d.bytesDownloaded * 100 / d.totalBytesToDownload).toInt()
                                _state.update {
                                    state.value.copy(
                                        isInstalling = true,
                                        progressState = progress
                                    )
                                }
                                if (progress == 100) {
                                    _state.update {
                                        state.value.copy(
                                            isInstalling = false,
                                            isError = true,
                                            isLoading = false,
                                            errorMessage = "Downloaded Successfully",
                                            progressState = progress
                                        )
                                    }
                                    scan(context = context)
                                }
                            }

                            if (isTerminateState(update.installState)) {
                                moduleInstallClient.unregisterListener(this)
                                scan(context = context)
                            }
                        }

                        fun isTerminateState(@ModuleInstallStatusUpdate.InstallState state: Int): Boolean {
                            return state == PrintJobInfo.STATE_CANCELED || state == PrintJobInfo.STATE_COMPLETED || state == PrintJobInfo.STATE_FAILED
                        }
                    }

                    _state.update {
                        state.value.copy(
                            isInstalling = true,
                            isError = true,
                            errorMessage = "Downloading Started"
                        )
                    }


                    val moduleInstallRequest =
                        ModuleInstallRequest.newBuilder()
                            .addApi(scanner)
                            .setListener(listener)
                            .build()

                    moduleInstallClient
                        .installModules(moduleInstallRequest)
                        .addOnSuccessListener {
                            _state.update {
                                state.value.copy(
                                    isInstalling = false,
                                    isError = true,
                                    errorMessage = "Downloaded Successfully"
                                )
                            }
                            if (it.areModulesAlreadyInstalled()) {
                                scan(context = context)
                            }
                        }.addOnFailureListener {
                            _state.update {
                                state.value.copy(
                                    isInstalling = false,
                                    isError = true,
                                    errorMessage = "Please try after some time."
                                )
                            }
                        }

                }
            }
            .addOnCanceledListener {
                _state.update {
                    state.value.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = "Please try after some time."
                    )
                }
            }
            .addOnFailureListener {
                _state.update {
                    state.value.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = "Please try after some time."
                    )
                }
            }

    }

    fun scan(context: Context) {
        val scanner = GmsBarcodeScanning.getClient(context as Activity, options)
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                val barCode = barcode.rawValue
                try {
                    if (barCode != null) {
                        val bookingId = decrypt(barCode)
                        val rData = Gson().fromJson(bookingId, BookingIdClass::class.java)
                        val date = dateFormat.parse(rData.issued_at)?.time
                            ?: throw Exception("InCorrect Date")
                        val currentDateTime =
                            Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata")).time.time
                        val diff = (currentDateTime - date) / 1000

                        if (diff > 10) {
                            _state.update {
                                state.value.copy(
                                    isError = true,
                                    isLoading = false,
                                    bookingId = null,
                                    errorMessage = "Qr Code Expired."
                                )
                            }

                        } else {
                           viewModelScope.launch {
                               withContext(Dispatchers.IO){
                                   checkInGuest(barCode)
                               }
                           }
                        }
                    } else {
                        _state.update {
                            state.value.copy(
                                isError = true,
                                isLoading = false,
                                bookingId = null,
                                errorMessage = "Wrong Qr Code"
                            )
                        }
                    }

                } catch (err: Exception) {
                    err.printStackTrace()
                    _state.update {
                        state.value.copy(
                            isError = true,
                            isLoading = false,
                            bookingId = null,
                            errorMessage = "Wrong Qr Code"
                        )
                    }
                }

            }
            .addOnCanceledListener {
                _state.update {
                    state.value.copy(
                        isError = true,
                        isLoading = false,
                        errorMessage = "Operation Cancelled"
                    )
                }
            }
            .addOnFailureListener { e ->
                _state.update {
                    state.value.copy(
                        isError = true,
                        isLoading = false,
                        errorMessage = "Operation Failed"
                    )
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


    private fun decrypt(encryptedData: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")

        val secretKey = "YouKnowWhatDoesChutiyapaStandFor";
        val iv = "DINESHPRAJAPATIS";

        val secretKeySpec = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
        val ivParameterSpec = IvParameterSpec(iv.toByteArray(Charsets.UTF_8))

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)

        val decodedEncryptedData = hexStringToByteArray(encryptedData)
        val decryptedByteArray = cipher.doFinal(decodedEncryptedData)
        return String(decryptedByteArray)
    }

    private fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] =
                ((Character.digit(s[i], 16) shl 4) + Character.digit(s[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }

    private suspend fun checkInGuest(barCode: String) {

        _state.update {
            state.value.copy(
                isLoading = true
            )
        }

        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("Text", "Plain"))
            }
        }

        try {
            val token = authTokenUseCases.getTokenUseCases()

            val response: String = client.post(Constant.checkInRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                }
                parameter("code", barCode)
            }.body()

            val rData = Gson().fromJson(response, Response::class.java)
            if (!rData.status) {
                _state.update {
                    state.value.copy(
                        isError = true,
                        isLoading = false,
                        errorMessage = rData.message
                    )
                }
                return
            }
            _state.update {
                state.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = rData.message
                )
            }

            val booking = Gson().fromJson(rData.data, BookingModel::class.java)
            bookingUseCases.addBookingUseCases(listOf(booking))


        } catch (err: java.lang.Exception) {
            err.printStackTrace()
            _state.update {
                state.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = err.message ?: "Please try after some time"
                )
            }
        }

    }

    fun checkInByCode(code: String) {
        if(code.toDoubleOrNull() == null){
            _state.update {
                state.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = "Please enter correct code"
                )
            }
            return
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                checkInGuest(
                    barCode = code
                )
            }
        }
    }

}

