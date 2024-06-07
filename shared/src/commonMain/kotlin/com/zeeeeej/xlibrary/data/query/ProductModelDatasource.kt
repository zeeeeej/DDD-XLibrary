package com.zeeeeej.xlibrary.data.query

import com.zeeeeej.xlibrary.util.xlog

interface ProductModelDatasource {
    fun list(): List<ProductModel>

    fun add(productModel: ProductModel)

    fun delete(model: String)

    fun edit(productModel: ProductModel)

    fun find(model: String): ProductModel?
}


internal class ProductModelDatasourceImpl : ProductModelDatasource {

    companion object {
        private const val TAG = "【ProductModelDatasourceImpl】"
    }

    private val list: MutableList<ProductModel> = mutableListOf()
    override fun list(): List<ProductModel> {
        xlog.d(TAG, "list")
        return list.toList()
    }

    override fun add(productModel: ProductModel) {
        xlog.d(TAG, "add $productModel")
        list.add(productModel)
    }

    override fun delete(model: String) {
        xlog.d(TAG, "delete $model")
        list.removeAll { it.model == model }
    }

    override fun edit(productModel: ProductModel) {
        xlog.d(TAG, "edit $productModel")
        list.removeAll { it.model == productModel.model }
        list.add(productModel)
    }

    override fun find(model: String): ProductModel? {
        xlog.d(TAG, "find $model")
        return list.singleOrNull { it.model == model }
    }

}