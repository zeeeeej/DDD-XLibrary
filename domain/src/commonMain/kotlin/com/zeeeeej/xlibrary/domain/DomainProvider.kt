package com.zeeeeej.xlibrary.domain

import com.zeeeeej.xlibrary.domain.core.DomainException
import com.zeeeeej.xlibrary.domain.product.ProductRepository
import kotlin.native.concurrent.ThreadLocal

interface DomainProvider {
    fun productRepository(): ProductRepository
}

@ThreadLocal
object DomainProvideOwner {
    private var _provider: DomainProvider? = null
    val provider: DomainProvider
        get() = _provider ?: throw DomainException("设置DomainProvider:fun provider(provider: DomainProvider)  ")

    fun provider(provider: DomainProvider) {
        _provider = provider
    }
}

