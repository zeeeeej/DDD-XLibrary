package com.zeeeeej.xlibrary.domain.util

interface XLog {

    fun d(tag: String, msg: String)
    fun i(tag: String, msg: String)
    fun w(tag: String, msg: String)
    fun e(tag: String, msg: String)
}

private const val TAG = "domain"
internal val xlog: DefaultLog = DefaultLog(TAG)

class DefaultLog(private val moduleTag: String) : XLog {
    override fun d(tag: String, msg: String) {
        println("D<<${moduleTag}>>[$tag]$msg")
    }

    override fun i(tag: String, msg: String) {
        println("I<<${moduleTag}>> [$tag]$msg")
    }

    override fun w(tag: String, msg: String) {
        println("W<<${moduleTag}>> [$tag]$msg")
    }

    override fun e(tag: String, msg: String) {
        println("E<<${moduleTag}>> [$tag]$msg")
    }

}