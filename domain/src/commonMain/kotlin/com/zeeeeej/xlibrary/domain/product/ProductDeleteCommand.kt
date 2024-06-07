package com.zeeeeej.xlibrary.domain.product

import com.zeeeeej.xlibrary.domain.core.CommandHandler
import com.zeeeeej.xlibrary.domain.core.DomainCommand
import com.zeeeeej.xlibrary.domain.core.DomainEventQueue

/**
 * 删除产品命令
 */
data class ProductDeleteCommand(
    val model: String,
) : DomainCommand

/**
 * * 删除产品命令处理器
 */
class ProductDeleteCommandHandler(private val productRepository: ProductRepository) :
    CommandHandler<ProductDeleteCommand> {
    override operator fun invoke(eventQueue: DomainEventQueue, command: ProductDeleteCommand) {
        val product: Product = this.productRepository.findOrThrow(command.model)
        val delete = product.delete(eventQueue)
        if (delete) {
            productRepository.delete(product)
        }

    }
}