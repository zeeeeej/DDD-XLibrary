package com.zeeeeej.xlibrary.domain.core

/**
 * 领域事件监听器
 */
interface DomainEventListener {
    fun onEvent(queue: DomainEventQueue, event: DomainEvent)
}