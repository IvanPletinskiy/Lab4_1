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
        TODO("Not yet implemented")
    }

    fun getChar(keyLetter: Char, textLetter: Char): Char {
        return A + (keyLetter.toUpperCase() - A + textLetter.toUpperCase().toInt() - A.toInt()) % (RUSSIAN_LETTERS_COUNT - 1)
    }

    companion object {
        const val RUSSIAN_LETTERS_COUNT = 33
        const val A = 'А'
    }
}

fun main() {
    val decimationMethod = VijinerMethod()
    val decodedText = "Криптография"
    val key = "ВАНЯ"
    println("Encode: ${(decimationMethod.encode(decodedText, key) as Result.Success).value}")

//    val encoded = "Ibwvhsubcvxwjsno"
//    println("Decode: ${(decimationMethod.decode(encoded, key) as Result.Success).value}")
}