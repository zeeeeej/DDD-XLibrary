package com.zeeeeej.xlibrary.data.domain

import com.zeeeeej.xlibrary.domain.core.DomainEvent
import com.zeeeeej.xlibrary.domain.core.DomainEventQueue

class SimpleEventQueue : DomainEventQueue {
    private val queue: MutableList<DomainEvent> = mutableListOf()
    override fun enqueue(event: DomainEvent) {
        queue.add(event)
    }

    override fun list(): List<DomainEvent> {
        return queue.toList()
    }
}