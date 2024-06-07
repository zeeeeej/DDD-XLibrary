package com.zeeeeej.xlibrary.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zeeeeej.xlibrary.compontent.CommonNaviUI
import com.zeeeeej.xlibrary.compontent.NavBackStackEntryInfo

@Composable
fun DDDApp() {
    Box(Modifier.fillMaxSize().padding(16.dp)) {
        val navController = rememberNavController()
        val navState by navController.currentBackStackEntryAsState()

        NavHost(navController, startDestination = Home.route) {
            composable(Home.route) {
                HomeScreen(onClick = {
                    navController.navigate(this.route)
                })
            }

            composable(Menu.Product.route) {
                CommonNaviUI(title = Menu.Product.text, onBack = { navController.popBackStack() }) {
                    ProductScreen()
                }
            }

            composable(Menu.Device.route) {
                CommonNaviUI(title = Menu.Device.text, onBack = { navController.popBackStack() }) {
                    DeviceScreen()
                }
            }

            composable(Menu.Logger.route) {
                CommonNaviUI(title = Menu.Logger.text, onBack = { navController.popBackStack() }) {
                    LoggerScreen()
                }
            }
        }

        if (navState != null) {
            navState?.NavBackStackEntryInfo(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomStart)
            )
        }

    }
}

val NavBackStackEntry.info: String
    get() = """
        destination :   ${this.destination}
        id          :   ${this.id}
        arguments   :   ${this.arguments}
    """.trimIndent()