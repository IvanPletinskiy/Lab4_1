package com.handen.lab

class VijinerMethod() : Method {
    override fun encode(text: String, key: String): Result<String> {
        val filtered = text.filter {
            (it in 'а'..'я') || (it in 'А'..'Я')
        }.toUpperCase().mapIndexed { index, textLetter ->
            val keyLetter = A + (key[index % key.length] - A + index / key.length) % (RUSSIAN_LETTERS_COUNT - 1)
            print(keyLetter)
            getChar(keyLetter, textLetter)
        }

        return Result.Success(String(filtered.toCharArray()))
    }

    override fun decode(text: String, key: String): Result<String> {
        val filtered = text.filter {
            (it in 'а'..'я') || (it in 'А'..'Я')
        }.toUpperCase().mapIndexed { index, textLetter ->
            val keyLetter = A + (key[index % key.length] - A + index / key.length) % (RUSSIAN_LETTERS_COUNT - 1)
            print(keyLetter)
            getSourceChar(keyLetter, textLetter)
        }

        return Result.Success(String(filtered.toCharArray()))
    }

    fun getChar(keyLetter: Char, textLetter: Char): Char {
        return A + (keyLetter.toUpperCase() - A + textLetter.toUpperCase().toInt() - A.toInt()) % (RUSSIAN_LETTERS_COUNT - 1)
    }

    fun getSourceChar(keyLetter: Char, encodedLetter: Char): Char {
        val index = (encodedLetter + RUSSIAN_LETTERS_COUNT - keyLetter - 1) % (RUSSIAN_LETTERS_COUNT - 1)
        return A + index
    }

    override fun toString(): String {
        return "Метод Вижнера, прогрессивный ключ"
    }

    companion object {
        const val RUSSIAN_LETTERS_COUNT = 33
        const val A = 'А'
    }
}

fun main() {
    val vijinerMethod = VijinerMethod()
    val decodedText = "Криптография"
    val key = "ВАНЯ"
    println("Encode: ${(vijinerMethod.encode(decodedText, key) as Result.Success).value}")

    val encoded = "МРХОХПСРДЦЧА"
    println("Decode: ${(vijinerMethod.decode(encoded, key) as Result.Success).value}")
}