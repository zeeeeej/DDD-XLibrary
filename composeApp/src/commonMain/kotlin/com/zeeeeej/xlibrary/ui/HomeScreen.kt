package com.zeeeeej.xlibrary.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ddd_xlibrary.composeapp.generated.resources.Res
import ddd_xlibrary.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource


@Composable
internal fun HomeScreen(onClick: Menu.() -> Unit) {
    val list by remember {
        mutableStateOf(Menu.entries.map { it })
    }
    Column (Modifier.fillMaxSize()){
        Text("DDD")
        Text("领域驱动模型 模型驱动软件设计")
        MenuList(list, onClick)
    }
}

@Composable
private fun MenuList(list: List<Menu>, onClick: Menu.() -> Unit) {
    LazyColumn {
        items(list, { it.toString() }) {
            MenuItem(it) {
                it.onClick()
            }
        }
    }
}

@Composable
private fun MenuItem(menu: Menu, onClick: () -> Unit) {

    Row(
        modifier = Modifier.clickable(onClick = onClick).fillMaxWidth().padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(Res.drawable.compose_multiplatform),
            null,
            modifier = Modifier.size(44.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(menu.name)
    }
}

