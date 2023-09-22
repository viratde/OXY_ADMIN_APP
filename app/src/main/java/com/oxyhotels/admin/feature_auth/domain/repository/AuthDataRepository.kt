package com.oxyhotels.admin.feature_auth.domain.repository

interface AuthDataRepository {

    suspend fun getToken():String?

    suspend fun setToken(authToken:String)

    suspend fun deleteToken()

}