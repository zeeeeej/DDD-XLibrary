package com.zeeeeej.xlibrary.domain.product

import com.zeeeeej.xlibrary.domain.core.DomainEvent

data class ProductAddedEvent(
    val model: String,
    val communicationType: CommunicationType,
    val tsl: Tsl? = null,
) : DomainEvent

data class ProductDeletedEvent(
    val model: String,
) : DomainEvent

data class ProductTslParsedEvent(
    val model: String,
    val tsl: Tsl?,
) : DomainEvent