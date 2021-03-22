package com.handen.lab

import kotlin.experimental.xor

class LFSR(private val state: String) {
    var list = listOf<Byte>()
    private var lastEmitted: Byte? = null

    init {
        list = state.toCharArray().map { if (it == '0') 0 else 1 }
    }

    private fun step() {
        val new  = list.first() xor list.last()
        lastEmitted = list.first()
        list = list.drop(1) + new
    }

    fun nextChar(): Byte {
        step()
        return lastEmitted!!
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
    val lfsr = LFSR("1111")
    val keyChars = mutableListOf<Byte>()
    for(i in 0 until 16) {
        println("Current lfsr state:${lfsr.list.joinToString("")}")
        keyChars.add(lfsr.nextChar())
    }
    println("Generated key:${keyChars.joinToString("")}")
}