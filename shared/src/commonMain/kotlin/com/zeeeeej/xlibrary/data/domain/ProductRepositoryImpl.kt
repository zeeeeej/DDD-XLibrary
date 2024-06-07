package com.zeeeeej.xlibrary.data.domain

import com.zeeeeej.xlibrary.domain.core.DomainException
import com.zeeeeej.xlibrary.domain.product.Product
import com.zeeeeej.xlibrary.domain.product.ProductRepository
import com.zeeeeej.xlibrary.util.xlog

internal class ProductRepositoryImpl : ProductRepository {
    companion object {
        private const val TAG = "【ProductRepository】"
    }

    private val list: MutableList<Product> = mutableListOf()
    override fun add(product: Product) {
        xlog.d(TAG,"add $product")
        list.add(product)
    }

    override fun findOrThrow(model: String): Product {
        xlog.d(TAG,"findOrThrow $model")
        return try {
            list.single { it.model == model }
        } catch (e: Throwable) {
            throw DomainException(message = null, cause = e)
        }
    }

    override fun find(model: String): Product? {
        xlog.d(TAG,"find $model")
        return list.singleOrNull(){model == it.model}
    }

    override fun delete(product: Product) {
        xlog.d(TAG,"delete $product")
        list.removeAll { it.model == product.model }
    }

    override fun update(product: Product) {
        xlog.d(TAG,"update $product")
        list.removeAll { it.model == product.model }
        list.add(product)

    }


}