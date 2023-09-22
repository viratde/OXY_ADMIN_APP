package com.oxyhotels.admin.feature_auth.domain.use_cases

data class AuthTokenUseCases (
    val getTokenUseCases: GetTokenUseCases,
    val setTokenUseCases: SetTokenUseCases,
    val deleteTokenUseCases: DeleteTokenUseCases,
    )