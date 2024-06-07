package com.zeeeeej.xlibrary.ui

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.zeeeeej.xlibrary.ProductModelRepository
import com.zeeeeej.xlibrary.domain.product.CommunicationType
import com.zeeeeej.xlibrary.mvi.MVIIntent
import com.zeeeeej.xlibrary.mvi.MVIViewModel
import com.zeeeeej.xlibrary.mvi.MVIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal data class ProductVo(
    val model: String,
    val communicationType: CommunicationType,
    val json: String?,
)

@Stable
internal data class ProductScreenState(
    val list: List<ProductVo> = emptyList(),
    val msg: String = "",
    val loading: String = "",
) : MVIState

internal typealias AddProductParam = Triple<String, CommunicationType, String?>

internal sealed interface ProductIntent : MVIIntent {
    data class AddProject(val project: AddProductParam) : ProductIntent
    data class DeleteProject(val productVo: ProductVo) : ProductIntent
    data class ParseProject(val productVo: ProductVo) : ProductIntent
    data object ListProject : ProductIntent
    data class Msg(val msg: String) : ProductIntent
    data class Loading(val loading: String) : ProductIntent
}

internal class ProductScreenVM :
    MVIViewModel<ProductScreenState, ProductIntent>(ProductScreenState()) {

    private val dddController: ProductModelRepository = ProductModelRepository


    override fun MutableStateFlow<ProductScreenState>.handle(intent: ProductIntent) {
        when (intent) {
            is ProductIntent.AddProject -> {
                viewModelScope.launch {
                    try {
                        dispatch(ProductIntent.Msg(""))
                        dispatch(ProductIntent.Loading("添加中..."))
                        val (model, type, json) = intent.project
                        withContext(Dispatchers.IO) {
                            delay(1000)
                            dddController.addProduct(model, type, json ?: "")
                        }
                        dispatch(ProductIntent.ListProject)
                    } catch (e: Throwable) {
                        dispatch(ProductIntent.Msg(e.message ?: "add 出错"))
                        dispatch(ProductIntent.Loading(""))
                    } finally {

                    }
                }

            }

            is ProductIntent.DeleteProject -> {
                viewModelScope.launch {
                    try {
                        dispatch(ProductIntent.Msg(""))
                        dispatch(ProductIntent.Loading("删除中..."))
                        withContext(Dispatchers.IO) {
                            delay(1000)
                            dddController.deleteProduct(intent.productVo.model)
                        }
                        dispatch(ProductIntent.ListProject)
                    } catch (e: Exception) {
                        dispatch(ProductIntent.Msg(e.message ?: "delete 出错"))
                        dispatch(ProductIntent.Loading(""))
                    }
                }
            }

            ProductIntent.ListProject -> {
                viewModelScope.launch {
                    try {
                        dispatch(ProductIntent.Msg(""))
                        dispatch(ProductIntent.Loading("加载列表中..."))
                        val newList = withContext(Dispatchers.IO) {
                            delay(500)
                            val productList = dddController.findAllProduct()
                            productList.map {
                                ProductVo(
                                    model = it.model,
                                    communicationType = it.communicationType,
                                    json = it.tsl?.source
                                )
                            }
                        }

                        this@handle.value = state.value.copy(list = newList)
                    } catch (e: Exception) {
                        dispatch(ProductIntent.Msg(e.message ?: "list 出错"))
                    } finally {
                        dispatch(ProductIntent.Loading(""))
                    }
                }

            }

            is ProductIntent.ParseProject -> {
                viewModelScope.launch {
                    try {
                        dispatch(ProductIntent.Msg(""))
                        dispatch(ProductIntent.Loading("解析中..."))
                        withContext(Dispatchers.IO) {
                            delay(1000)
                            dddController.parseTsl(
                                intent.productVo.model,
                                intent.productVo.json ?: ""
                            )
                        }
                        dispatch(ProductIntent.ListProject)
                    } catch (e: Exception) {
                        dispatch(ProductIntent.Msg(e.message ?: "parse 出错"))
                        dispatch(ProductIntent.Loading(""))
                    }
                }
            }

            is ProductIntent.Loading -> this.value = state.value.copy(loading = intent.loading)
            is ProductIntent.Msg -> this.value = state.value.copy(msg = intent.msg)
        }
    }
}