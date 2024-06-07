package com.zeeeeej.xlibrary.domain.policy

import com.zeeeeej.xlibrary.domain.core.DomainEvent
import com.zeeeeej.xlibrary.domain.core.DomainEventQueue
import com.zeeeeej.xlibrary.domain.core.DomainPolicy
import com.zeeeeej.xlibrary.domain.product.ProductDeletedEvent

class ProductDeletedPolicy: DomainPolicy {
    override fun onEvent(queue: DomainEventQueue, event: DomainEvent) {
        if (event is ProductDeletedEvent) {
            println("准备删除设备...")
        }
    }
}

