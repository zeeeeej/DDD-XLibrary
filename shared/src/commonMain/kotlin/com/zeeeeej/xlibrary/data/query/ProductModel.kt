package com.zeeeeej.xlibrary.data.query

import com.zeeeeej.xlibrary.domain.product.CommunicationType
import com.zeeeeej.xlibrary.domain.product.Tsl
import com.zeeeeej.xlibrary.util.xlog

class ProductModel(
    val model: String,
    val communicationType: CommunicationType,
    val tsl: Tsl?,
) {
    override fun toString(): String {
        return """
            |{
            |    "model":${model},
            |    "communicationType":${communicationType},
            |    "tsl":${tsl}
            |}
        """.trimMargin()
    }
}


