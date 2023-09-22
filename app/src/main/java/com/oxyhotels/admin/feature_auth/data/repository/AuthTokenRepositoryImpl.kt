package com.oxyhotels.admin.feature_auth.data.repository

import android.content.SharedPreferences
import com.oxyhotels.admin.feature_auth.domain.repository.AuthDataRepository

class AuthTokenRepositoryImpl (
    private val sharedPreferences: SharedPreferences
        ): AuthDataRepository {

    override suspend fun getToken(): String? {
        return sharedPreferences.getString("authToken",null)
    }

    override suspend fun setToken(authToken: String) {
        sharedPreferences.edit().putString("authToken",authToken).apply()
    }

    override suspend fun deleteToken() {
        sharedPreferences.edit().remove("authToken").apply()
    }

}