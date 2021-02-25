package com.handen.lab

internal interface Method {
    fun encode(text: String, key: String): Result<String>
    fun decode(text: String, key: String): Result<String>

    val encodedPath: String
    val decodedPath: String
}