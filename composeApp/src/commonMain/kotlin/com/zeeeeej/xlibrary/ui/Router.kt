package com.zeeeeej.xlibrary.ui

internal sealed interface Router {
    val route: String
}

internal data object Home : Router {
    override val route: String
        get() = "Home"
}

internal enum class Menu : Router {
    Product,
    Device,
    Logger
    ;

    override val route: String
        get() = this.name
}

internal val Menu.text: String
    get() = when (this) {
        Menu.Product -> "测试Product"
        Menu.Device -> "测试Device"
        Menu.Logger -> "测试Logger"
    }