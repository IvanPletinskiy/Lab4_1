package com.handen.lab

internal interface Method {
    fun encode(text: String, key: String): String
    fun decode(text: String, key: String): String
}