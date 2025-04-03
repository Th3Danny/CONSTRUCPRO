package com.valhallatech.civibridge.register.domain

import com.valhallatech.civibridge.register.data.model.RegisterRequest
import com.valhallatech.civibridge.register.data.repository.RegisterRepository

class RegisterUseCase(private val repository: RegisterRepository) {
    suspend operator fun invoke(request: RegisterRequest): Result<Unit> {
        return repository.register(request)
    }
}
