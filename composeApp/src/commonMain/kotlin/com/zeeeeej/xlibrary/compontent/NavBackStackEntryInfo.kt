package com.zeeeeej.xlibrary.compontent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavBackStackEntry

@Composable
fun NavBackStackEntry.NavBackStackEntryInfo(modifier: Modifier) {
    Column(modifier = modifier) {

        Row {
            Text(text = "[id]", fontWeight = FontWeight.Bold, color = Color.Red)
            Text(this@NavBackStackEntryInfo.id, color = Color.Red)
        }

        Row {
            Text("[destination]", fontWeight = FontWeight.Bold, color = Color.Red)
            Column {
                Text(("route=" + this@NavBackStackEntryInfo.destination.route), color = Color.Red)

                Text(
                    "displayName=" + this@NavBackStackEntryInfo.destination.displayName,
                    color = Color.Red
                )
                Text(
                    "navigatorName=" + this@NavBackStackEntryInfo.destination.navigatorName,
                    color = Color.Red
                )
                Text(
                    "arguments=" + this@NavBackStackEntryInfo.destination.arguments.map { (k, v) -> "$k $v" }
                        .joinToString { it },
                    color = Color.Red
                )
            }
        }

        Row {
            Text("[arguments]", fontWeight = FontWeight.Bold, color = Color.Red)
            Text(this@NavBackStackEntryInfo.arguments.toString(), color = Color.Red)
        }
    }
}