package com.oxyhotels.admin.feature_auth.domain.use_cases

import com.oxyhotels.admin.feature_auth.domain.repository.AuthDataRepository

class DeleteTokenUseCases(
    private val authDataRepository: AuthDataRepository
) {
    suspend operator fun invoke(){
        authDataRepository.deleteToken()
    }

}