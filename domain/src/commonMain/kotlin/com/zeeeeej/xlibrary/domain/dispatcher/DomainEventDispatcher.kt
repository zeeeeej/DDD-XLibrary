package com.zeeeeej.xlibrary.domain.dispatcher

import com.zeeeeej.xlibrary.domain.core.DomainEventListener
import com.zeeeeej.xlibrary.domain.core.DomainEventQueue
import com.zeeeeej.xlibrary.domain.policy.PolicyStore

/**
 * 领域？
 */
interface DomainEventDispatcher<in Queue : DomainEventQueue> {
    fun dispatchNow(queue: Queue)
}

/**
 * 领域？
 */
class SyncDomainEventDispatcher(
    list: List<DomainEventListener>,
) : DomainEventDispatcher<DomainEventQueue> {
    private val list: List<DomainEventListener> = PolicyStore.list + list
    override fun dispatchNow(queue: DomainEventQueue) {
        queue.list().forEach { event ->
            list.forEach { listener ->
                listener.onEvent(queue, event)
            }
        }
    }
}