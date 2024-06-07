package com.zeeeeej.xlibrary.compontent

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun CommonRefresh(
    modifier: Modifier,
    refreshing: Boolean,
    onRefresh: suspend () -> Unit,
    content: @Composable () -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()

    @OptIn(ExperimentalMaterialApi::class)
    val pullRefreshState =
        rememberPullRefreshState(refreshing, onRefresh = {
            coroutineScope.launch {
                onRefresh()
            }
        })

    @OptIn(ExperimentalMaterialApi::class)
    Box(
        modifier
            .pullRefresh(pullRefreshState, true),
    ) {
        content()

        PullRefreshIndicator(
            refreshing = refreshing, state = pullRefreshState, modifier = Modifier.align(
                Alignment.TopCenter
            )
        )
    }
}