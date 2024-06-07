package com.zeeeeej.xlibrary.data.domain

import com.zeeeeej.xlibrary.domain.dispatcher.DomainEventDispatcher
import com.zeeeeej.xlibrary.domain.core.DomainEventQueue
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal interface CommandInvoker {
    suspend operator fun <R, Queue> invoke(block: (DomainEventQueue) -> R): R where  Queue : DomainEventQueue
}

internal class OneTransactionCommandInvoker(
    private val domainEventDispatcher: DomainEventDispatcher<SimpleEventQueue>,
) : CommandInvoker {
    override suspend fun <R, Queue> invoke(block: (DomainEventQueue) -> R): R where  Queue : DomainEventQueue {
        return suspendCancellableCoroutine { continuation ->
            try {
                val eventQueue = SimpleEventQueue()
                val result = block.invoke(eventQueue)
                this.domainEventDispatcher.dispatchNow(eventQueue)
                continuation.resume(result)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    companion object {

    }

}