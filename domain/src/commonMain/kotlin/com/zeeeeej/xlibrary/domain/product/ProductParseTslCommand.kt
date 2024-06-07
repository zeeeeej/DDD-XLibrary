package com.zeeeeej.xlibrary.domain.product

import com.zeeeeej.xlibrary.domain.core.CommandHandler
import com.zeeeeej.xlibrary.domain.core.DomainCommand
import com.zeeeeej.xlibrary.domain.core.DomainEventQueue

/**
 * 解析产品Tsl命令
 */
data class ProductParseTslCommand(
    val model: String,
    val json: String,
) : DomainCommand

/**
 * * 解析产品Tsl命令处理器
 */
class ProductParseTslCommandHandler(private val productRepository: ProductRepository) :
    CommandHandler<ProductParseTslCommand> {
    override operator fun invoke(eventQueue: DomainEventQueue, command: ProductParseTslCommand) {
        val product: Product = this.productRepository.findOrThrow(command.model)
        val parseTsl = product.parseTsl(eventQueue, command.json) // 处理tsl
        if (parseTsl) {
            productRepository.update(product) // 存储到数据库
        }
    }
}