package com.handen.lab

import kotlin.experimental.and
import kotlin.experimental.xor

class LFSR(private val state: String) {
    var list = listOf<Byte>()
    private var lastEmitted: Byte? = null

    init {
        list = state.toCharArray().map { if (it == '0') 0 else 1 }
    }

    private fun step() {
        val new = list.first() xor list.last()
        lastEmitted = list.first()
        list = list.drop(1) + new
    }

    fun nextChar(): Byte {
        step()
        return lastEmitted!!
    }

    fun encodeString(string: String): String {
        val keyChars = mutableListOf<Byte>()
        val result = string.map {
            val byte: Byte = if (it == '0') 0 else 1
            val keyChar = nextChar()
            keyChars.add(keyChar)
            val resultChar = byte xor keyChar
            resultChar
        }
//        println("Key:${keyChars.joinToString("")}")
        return result.joinToString("")
    }

    fun decodeString(string: String): String {
        val result = string.map {
            val byte: Byte = if (it == '0') 0 else 1
            val keyChar = nextChar()
            val resultChar = byte xor keyChar
            resultChar
        }
        return result.joinToString("")
    }

//    private fun nextByte(): Byte {
//        val chars = mutableListOf<Byte>()
//        for(i in 0 until 8) {
//            step()
//            chars.add(lastEmitted!!)
//        }
//    }
}

fun main() {
    fun encodeString(lfsr: LFSR, string: String): String {
        val keyChars = mutableListOf<Byte>()
        val result = string.map {
            val byte: Byte = if (it == '0') 0 else 1
            val keyChar = lfsr.nextChar()
            keyChars.add(keyChar)
            val resultChar = byte xor keyChar
            resultChar
        }
        println("Key:${keyChars.joinToString("")}")
        return result.joinToString("")
    }

    fun decodeString(lfsr: LFSR, string: String): String {
        val result = string.map {
            val byte: Byte = if (it == '0') 0 else 1
            val keyChar = lfsr.nextChar()
            val resultChar = byte xor keyChar
            resultChar
        }
        return result.joinToString("")
    }

    val lfsr = LFSR("1111")
    val keyChars = mutableListOf<Byte>()
//    for (i in 0 until 16) {
//        println("Current lfsr state:${lfsr.list.joinToString("")}")
//        keyChars.add(lfsr.nextChar())
//    }
//    println("Generated key:${keyChars.joinToString("")}")

    val encoded = encodeString(LFSR("1111"), "10101010")
    println("EncodedString:$encoded")
    val decoded = decodeString(LFSR("1111"), encoded)
    println("DecodedString:$decoded")
}