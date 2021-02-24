package com.handen.lab

import kotlin.math.max

class DecimationMethod() : Method {
    override fun encode(text: String, key: String): Result<String> {
        val keyInt = key.toInt()
        if (keyInt < 1) {
            return Result.Error("Ошибка, ключ не может быть меньше 1.")
        }
        val checkResult = if (max(keyInt, ENGLISH_LETTERS_COUNT) > 1) {
            (2..maxOf(keyInt, ENGLISH_LETTERS_COUNT)).none { keyInt % it == 0 && ENGLISH_LETTERS_COUNT % it == 0 }
        } else {
            true
        }

        if (!checkResult) {
            return Result.Error("Ошибка, ключ и 26 не взаимно простые числа.")
        }

        val text = text.filter {
            (it in 'a'..'z') || (it in 'A'..'Z')
        }.map {
            val letter = if (it in 'a'..'z') {
                'a' + ((it - 'a' + 1) * keyInt % ENGLISH_LETTERS_COUNT) - 1
            } else {
                'A' + ((it - 'A' + 1) * keyInt % ENGLISH_LETTERS_COUNT) - 1
            }

            letter
        }.toCharArray()

        return Result.Success(String(text))
    }

    override fun decode(text: String, key: String): Result<String> {
        val keyInt = key.toInt()
        if (keyInt < 1) {
            return Result.Error("Ключ не может быть меньше 1.")
        }

        val checkResult = if (max(keyInt, ENGLISH_LETTERS_COUNT) > 1) {
            (2..maxOf(keyInt, ENGLISH_LETTERS_COUNT)).none { keyInt % it == 0 && ENGLISH_LETTERS_COUNT % it == 0 }
        } else {
            true
        }

        if (!checkResult) {
            return Result.Error("Ошибка, ключ и 26 не взаимно простые числа.")
        }

        val text = text.filter {
            (it in 'a'..'z') || (it in 'A'..'Z')
        }.map {
            if (it in 'a'..'z') {
                var char = it - 'a' + 1
                while (char % keyInt != 0) {
                    char += ENGLISH_LETTERS_COUNT
                }
                'a' - 1 + char / keyInt
            } else {
                var char = it - 'A' + 1
                while (char % keyInt != 0) {
                    char += ENGLISH_LETTERS_COUNT
                }
                'A' - 1 + char / keyInt
            }
        }

        return Result.Success(String(text.toCharArray()))
    }

    override fun toString(): String {
        return "Метод децимаций"
    }

    companion object {
        const val ENGLISH_LETTERS_COUNT = 26
    }
}

fun main() {
    val decimationMethod = DecimationMethod()
    val decodedText = "Cryptographylove"
    val key = "3"
    println("Encode: ${(decimationMethod.encode(decodedText, key) as Result.Success).value}")

    val encoded = "Ibwvhsubcvxwjsno"
    println("Decode: ${(decimationMethod.decode(encoded, key) as Result.Success).value}")
}