//package com.zeeeeej.xlibrary.demo
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//
//@Composable
//fun ShareElementDemo() {
//    var showDetails by remember {
//        mutableStateOf(false)
//    }
//    SharedTransitionLayout {
//        AnimatedContent(
//            showDetails,
//            label = "basic_transition"
//        ) { targetState ->
//            if (!targetState) {
//                MainContent(
//                    onShowDetails = {
//                        showDetails = true
//                    },
//                    animatedVisibilityScope = this@AnimatedContent,
//                    sharedTransitionScope = this@SharedTransitionLayout
//                )
//            } else {
//                DetailsContent(
//                    onBack = {
//                        showDetails = false
//                    },
//                    animatedVisibilityScope = this@AnimatedContent,
//                    sharedTransitionScope = this@SharedTransitionLayout
//                )
//            }
//        }
//    }
//}