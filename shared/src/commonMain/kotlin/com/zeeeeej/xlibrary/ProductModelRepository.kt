package com.zeeeeej.xlibrary

import com.zeeeeej.xlibrary.data.domain.CommandInvoker
import com.zeeeeej.xlibrary.domain.core.DomainEventListener
import com.zeeeeej.xlibrary.domain.DomainProvideOwner
import com.zeeeeej.xlibrary.domain.DomainProvider
import com.zeeeeej.xlibrary.data.domain.OneTransactionCommandInvoker
import com.zeeeeej.xlibrary.data.domain.ProductRepositoryImpl
import com.zeeeeej.xlibrary.data.domain.SimpleEventQueue
import com.zeeeeej.xlibrary.data.query.ProductModel
import com.zeeeeej.xlibrary.domain.product.CommunicationType
import com.zeeeeej.xlibrary.domain.product.ProductAddCommand
import com.zeeeeej.xlibrary.domain.product.ProductAddCommandHandler
import com.zeeeeej.xlibrary.domain.product.ProductDeleteCommand
import com.zeeeeej.xlibrary.domain.product.ProductDeleteCommandHandler
import com.zeeeeej.xlibrary.domain.product.ProductParseTslCommand
import com.zeeeeej.xlibrary.domain.product.ProductParseTslCommandHandler
import com.zeeeeej.xlibrary.domain.product.ProductRepository
import com.zeeeeej.xlibrary.data.query.ProductModelDatasource
import com.zeeeeej.xlibrary.data.query.ProductModelDatasourceImpl
import com.zeeeeej.xlibrary.data.query.ProductModelQuery
import com.zeeeeej.xlibrary.domain.dispatcher.SyncDomainEventDispatcher

object ProductModelRepository {

    private val productRepository = ProductRepositoryImpl()

    init {
        DomainProvideOwner.provider(object : DomainProvider {
            override fun productRepository(): ProductRepository {
                return productRepository
            }

        })
    }

    //region query
    private val productModelDatasource: ProductModelDatasource = ProductModelDatasourceImpl()
    private val productQuery = ProductModelQuery(productModelDatasource)

    //endregion

    private val _listenerList: MutableList<DomainEventListener> = mutableListOf()
    private val listenerList: List<DomainEventListener>
        get() = _listenerList

    init {
        _listenerList.add(productQuery)
    }

    private val dispatcher = SyncDomainEventDispatcher(listenerList)
    private val commandInvoker: CommandInvoker = OneTransactionCommandInvoker(dispatcher)
    private val productAddCommandHandler: ProductAddCommandHandler =
        ProductAddCommandHandler(productRepository)

    private val productDeleteCommandHandler: ProductDeleteCommandHandler =
        ProductDeleteCommandHandler(productRepository)

    private val productParseTslCommandHandler: ProductParseTslCommandHandler =
        ProductParseTslCommandHandler(productRepository)


    suspend fun addProduct(
        model: String,
        communicationType: CommunicationType,
        json: String,
    ) {
        commandInvoker<Unit, SimpleEventQueue> {
            productAddCommandHandler(
                it, ProductAddCommand(
                    model, communicationType, json
                )
            )
        }
    }

    suspend fun deleteProduct(model: String) {
        commandInvoker<Unit, SimpleEventQueue> {
            productDeleteCommandHandler(
                it, ProductDeleteCommand(
                    model
                )
            )
        }
    }

    suspend fun parseTsl(model: String, json: String) {
        commandInvoker<Unit, SimpleEventQueue> {
            productParseTslCommandHandler(
                it, ProductParseTslCommand(
                    model, json
                )
            )
        }
    }

    suspend fun findAllProduct(): List<ProductModel> {
        return commandInvoker<List<ProductModel>, SimpleEventQueue> {
            val list = productModelDatasource.list()
            println("所有的ProductModel:$list")
            list
        }
    }
}