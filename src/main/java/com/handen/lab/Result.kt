package com.handen.lab

sealed class Result<out T: Any>() {
    class Success<out T: Any>(val value: T): Result<T>()
    class Error(val message: String): Result<Nothing>()
}