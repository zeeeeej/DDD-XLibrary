package com.zeeeeej.xlibrary.domain.core
interface DomainEventQueue {
    fun enqueue(event: DomainEvent)

    fun list(): List<DomainEvent>

}