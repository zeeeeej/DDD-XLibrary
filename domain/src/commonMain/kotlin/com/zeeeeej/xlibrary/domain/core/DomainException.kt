package com.zeeeeej.xlibrary.domain.core

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

open class DomainException(message: String? = null, cause: Throwable? = null) :
    RuntimeException(message, cause)

open class DomainArgumentException(message: String? = null, cause: Throwable? = null) :
    RuntimeException(message, cause)

open class DomainStateException(message: String? = null, cause: Throwable? = null) :
    RuntimeException(message, cause)

class DomainCommandException(msg: String) : DomainException(msg)

@OptIn(ExperimentalContracts::class)
public inline fun requireDomain(value: Boolean, errorMsg: () -> Any): Unit {
    contract {
        returns() implies value
    }
    if (!value) {
        throw DomainArgumentException(message = errorMsg().toString())
    }
}

@OptIn(ExperimentalContracts::class)
public inline fun checkDomain(value: Boolean, errorMsg: () -> Any): Unit {
    contract {
        returns() implies value
    }
    if (!value) {
        throw DomainStateException(message = errorMsg().toString())
    }
}