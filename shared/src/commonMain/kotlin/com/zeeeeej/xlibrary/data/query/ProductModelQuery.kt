package com.zeeeeej.xlibrary.data.query

import com.zeeeeej.xlibrary.domain.core.DomainEvent
import com.zeeeeej.xlibrary.domain.core.DomainEventQueue
import com.zeeeeej.xlibrary.domain.core.DomainQuery
import com.zeeeeej.xlibrary.domain.product.ProductAddedEvent
import com.zeeeeej.xlibrary.domain.product.ProductDeletedEvent
import com.zeeeeej.xlibrary.domain.product.ProductTslParsedEvent

class ProductModelQuery(
    private val productModelDatasource: ProductModelDatasource,
) : DomainQuery {
    override fun onEvent(queue: DomainEventQueue, event: DomainEvent) {
        println("【ProductQuery】onEvent $event")
        when (event) {
            is ProductAddedEvent -> {
                productModelDatasource.add(
                    ProductModel(
                        model = event.model,
                        communicationType = event.communicationType,
                        tsl = event.tsl
                    )
                )
            }

            is ProductDeletedEvent -> {
                productModelDatasource.delete(event.model)
            }

            is ProductTslParsedEvent -> {
                val productModel = productModelDatasource.find(event.model) ?: return
                productModelDatasource.edit(
                    productModel = ProductModel(
                        model = productModel.model,
                        communicationType = productModel.communicationType,
                        tsl = event.tsl
                    )
                )
            }
        }
    }
}