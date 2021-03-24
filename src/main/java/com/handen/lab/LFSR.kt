package com.handen.lab

import kotlin.experimental.xor

@ExperimentalUnsignedTypes
class LFSR(private val state: String) {
    var list = listOf<Byte>()
    private var lastEmitted: Byte? = null
    private var generatedKeyChars: MutableList<Byte> = mutableListOf()

    init {
        list = state.toCharArray().map { if (it == '0') 0 else 1 }
    }

    private fun step() {
        val new = list.first() xor list[list.lastIndex - 1]
        lastEmitted = list.first()
        list = list.drop(1) + new
        generatedKeyChars.add(lastEmitted!!)
    }

    fun nextDigit(): Byte {
        step()
        return lastEmitted!!
    }

    fun getKey(): String {
        return generatedKeyChars.joinToString("")
    }

    private fun nextByte(): UByte {
        val chars = mutableListOf<Byte>()
        for(i in 0 until 8) {
            step()
            chars.add(lastEmitted!!)
        }
        return chars.joinToString("").toUByte(2)
    }

    fun encodeBytes(bytes: ByteArray): ByteArray {
        val encodedBytes = bytes.map {
            it.toUByte() xor nextByte()
        }.toUByteArray()

        return encodedBytes.toByteArray()
    }

    fun decodeBytes(bytes: ByteArray): ByteArray {
        val decodedBytes = bytes.map {
            it.toUByte() xor nextByte()
        }.toUByteArray()

        return decodedBytes.toByteArray()
    }
}