package com.zeeeeej.xlibrary.domain.product

import com.zeeeeej.xlibrary.domain.core.DomainEvent
import com.zeeeeej.xlibrary.domain.core.DomainEventQueue
import com.zeeeeej.xlibrary.domain.core.DomainException
import com.zeeeeej.xlibrary.domain.util.xlog
import kotlin.random.Random

interface Product {
    val model: String
    val communicationType: CommunicationType

    val tsl: Tsl?
    fun parseTsl(eventQueue: DomainEventQueue, json: String): Boolean
    fun add(eventQueue: DomainEventQueue): Boolean
    fun delete(eventQueue: DomainEventQueue): Boolean

}

enum class CommunicationType {
    歪夫艾, 肆基
    ;
}

internal class ProductImpl(
    override val model: String,
    override val communicationType: CommunicationType,
    tsl: Tsl? = null,
) : Product {

    private var _tsl: Tsl? = tsl
    private var added: Boolean = false
    override val tsl: Tsl?
        get() = _tsl

    override fun parseTsl(eventQueue: DomainEventQueue, json: String): Boolean {
        xlog.d("ProductImpl","parseTsl")
        // 解析tsl
        val tslTemp = TslImpl(source = json + Random.nextInt(10))
        // 更新聚合
        this._tsl = tslTemp
        // 发布消息
        eventQueue.enqueue(ProductTslParsedEvent(this.model, tslTemp))
        return true
    }

    override fun add(eventQueue: DomainEventQueue): Boolean {
        xlog.d("ProductImpl","add")
        if (added) {
            return false
        }
        this.added = true
        eventQueue.enqueue(ProductAddedEvent(this.model, this.communicationType, this.tsl))
        return true
    }

    override fun delete(eventQueue: DomainEventQueue): Boolean {
        xlog.d("ProductImpl","delete")
        if (!added) {
            return false
        }
        this.added = false
        eventQueue.enqueue(ProductDeletedEvent(this.model))
        return true
    }

}

interface ProductRepository {
    fun add(product: Product)

    @Throws(DomainException::class)
    fun findOrThrow(model: String): Product
    fun find(model: String): Product?
    fun delete(product: Product)
    fun update(product: Product)
}
