package com.zeeeeej.xlibrary.domain.policy

import com.zeeeeej.xlibrary.domain.core.DomainPolicy

object PolicyStore {

    val list: List<DomainPolicy> by lazy {
        listOf(
            ProductDeletedPolicy()
        )
    }
}