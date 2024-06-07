package com.zeeeeej.xlibrary.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zeeeeej.xlibrary.compontent.CommonRefresh
import com.zeeeeej.xlibrary.domain.product.CommunicationType

@Composable
internal fun ProductScreen() {
    val viewModel: ProductScreenVM = viewModel(){
        ProductScreenVM()
    }
    val state: ProductScreenState by viewModel.state.collectAsState()
    val msg by remember { derivedStateOf { state.msg } }
    val loading by remember { derivedStateOf { state.loading } }
    val refreshing by remember { derivedStateOf { loading.isNotEmpty() } }

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize()
        ) {
            DebugInfo(msg = msg, loading = loading)

            ProductAdd(
                modifier = Modifier.fillMaxWidth().border(width = 1.dp, color = Color.Red),
                onAdd = {
                    viewModel.dispatch(ProductIntent.AddProject(it))
                })

            Spacer(Modifier.height(8.dp))

            CommonRefresh(
                modifier = Modifier.weight(1f).fillMaxWidth()
                    .border(width = 1.dp, color = Color.Red),
                refreshing = refreshing,
                onRefresh = {
                    viewModel.dispatch(ProductIntent.ListProject)
                }
            ) {
                ProductList(
                    Modifier.fillMaxSize(),
                    state.list,
                    onClick = {
                        viewModel.dispatch(ProductIntent.ParseProject(this))
                    },
                    onDelete = {
                        viewModel.dispatch(ProductIntent.DeleteProject(this))
                    })
            }
        }
    }
}

@Composable
private fun DebugInfo(msg: String, loading: String) {
    Column {
        if (loading.isNotEmpty()) {
            Text(loading, color = Color.Green)
        }
        if (msg.isNotEmpty()) {
            Text(msg, color = Color.Red)
        }
    }
}

@Composable
private fun ProductAdd(modifier: Modifier, onAdd: (AddProductParam) -> Unit) {
    var model by remember { mutableStateOf("") }
    var communicationType by remember { mutableStateOf(CommunicationType.歪夫艾) }
    var json by remember { mutableStateOf("") }
    Column(modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("model:", fontWeight = FontWeight.Bold)
//            Text(model, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(8.dp))
            TextField(value = model, onValueChange = {
                model = it
            })
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("通信类型:", fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(8.dp))
            Text("WiFi", fontWeight = FontWeight.Bold)
            Checkbox(checked = communicationType == CommunicationType.歪夫艾, onCheckedChange = {
                if (it) {
                    communicationType = CommunicationType.歪夫艾
                }
            })
            Spacer(Modifier.width(8.dp))
            Text("4G", fontWeight = FontWeight.Bold)
            Checkbox(checked = communicationType == CommunicationType.肆基, onCheckedChange = {
                if (it) {
                    communicationType = CommunicationType.肆基
                }
            })
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Json:", fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(8.dp))
            TextField(value = json, onValueChange = {
                json = it
            })
        }

        Button(onClick = {
            onAdd(Triple(model, communicationType, json))
        }) {
            Text("添加")
        }

    }

}

@Composable
private fun ProductList(
    modifier: Modifier,
    list: List<ProductVo>,
    onClick: ProductVo.() -> Unit,
    onDelete: ProductVo.() -> Unit,
) {
    LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(list, { it.model }) {
            ProductItem(it, onClick = {
                it.onClick()
            }, onDelete = {
                it.onDelete()
            })
        }
    }
}

@Composable
private fun ProductItem(product: ProductVo, onClick: () -> Unit, onDelete: () -> Unit) {
    Row(
        Modifier.fillMaxWidth()
            .background(Color.Yellow.copy(alpha = .5f))
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier) {
            Text(product.model)
            Text(product.communicationType.name)
        }
        Text(product.json ?: "-", modifier = Modifier.weight(1f))
        Image(Icons.Default.Delete, "delete", modifier = Modifier.clickable(onClick = onDelete))
    }
}