package com.zeeeeej.xlibrary.domain.product

import com.zeeeeej.xlibrary.domain.core.CommandHandler
import com.zeeeeej.xlibrary.domain.core.DomainCommand
import com.zeeeeej.xlibrary.domain.core.DomainEventQueue
import com.zeeeeej.xlibrary.domain.core.requireDomain

/**
 * 添加产品
 */
data class ProductAddCommand(
    val model: String,
    val communicationType: CommunicationType,
    val tsl: String?,
) : DomainCommand

/**
 * 添加产品处理器
 */
class ProductAddCommandHandler(
    private val productRepository: ProductRepository,
) :
    CommandHandler<ProductAddCommand> {

    override operator fun invoke(eventQueue: DomainEventQueue, command: ProductAddCommand) {
        val product = this.productRepository.find(command.model)
        requireDomain(command.model.isNotBlank()) {
            "ProductAddCommandHandler失败，model=${command.model}为空"
        }
        requireDomain(product == null) {
            "ProductAddCommandHandler失败，已经存在model=${command.model}的产品"
        }
        val newProduct =
            ProductImpl(command.model, command.communicationType, TslImpl(command.tsl ?: ""))
        val add = newProduct.add(eventQueue)
        if (add) {
            productRepository.add(newProduct)
        }
    }
}

