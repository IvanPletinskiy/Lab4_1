package com.handen.lab

class VijinerMethod() : Method {

    val alphabet = arrayOf('А','Б','В','Г','Д','Е', 'Ё', 'Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х','Ц','Ч','Ш','Щ','Ъ','Ы','Ь','Э','Ю','Я')

    override fun encode(text: String, key: String): Result<String> {
        val upperKey = key.toUpperCase()
        val filtered = text.filter {
            (it in 'а'..'я') || (it in 'А'..'Я') || it == 'Ё'
        }.toUpperCase().mapIndexed { index, textLetter ->
            val upperKeyLetterIndex = alphabet.indexOf(upperKey[index % upperKey.length])
            val upperKeyLetter = alphabet[(upperKeyLetterIndex + index / upperKey.length) % alphabet.size]

            getChar(upperKeyLetter, textLetter)
        }

        return Result.Success(String(filtered.toCharArray()))
    }

    override fun decode(text: String, key: String): Result<String> {
        val upperKey = key.toUpperCase()
        val filtered = text.filter {
            (it in 'а'..'я') || (it in 'А'..'Я') || it == 'Ё'
        }.toUpperCase().mapIndexed { index, textLetter ->
            val rawIndex = alphabet.indexOf(upperKey[index % upperKey.length])
            val offset = index / upperKey.length
            val letterIndex = (rawIndex + offset) % alphabet.size
            val keyLetter = alphabet[letterIndex]
            getSourceChar(keyLetter, textLetter)
        }

        return Result.Success(String(filtered.toCharArray()))
    }

    fun getChar(keyLetter: Char, textLetter: Char): Char {
        return alphabet[(alphabet.indexOf(keyLetter) + alphabet.indexOf(textLetter)) % alphabet.size]
    }

    fun getSourceChar(keyLetter: Char, encodedLetter: Char): Char {
        val encodedIndex = alphabet.indexOf(encodedLetter)
        val keyLetterIndex = alphabet.indexOf(keyLetter)
        val index = (encodedIndex + alphabet.size - keyLetterIndex) % alphabet.size

        return alphabet[index]
    }

    override fun toString(): String {
        return "Метод Виженера, прогрессивный ключ"
    }

    override val encodedPath: String
        get() = "C:\\ti\\vigenere\\encoded.txt"
    override val decodedPath: String
        get() = "C:\\ti\\vigenere\\decoded.txt"
}

fun main() {
    val vijinerMethod = VijinerMethod()
    val sourceText = "ЁЛКИ"
    val key = "ВАНЯ"

    val encoded = (vijinerMethod.encode(sourceText, key) as Result.Success).value
    println("Encode: $encoded")
    val decoded = (vijinerMethod.decode(encoded, key) as Result.Success).value
    println("Decode: $decoded")
//    println("Decode: ${(vijinerMethod.decode(encoded, key) as Result.Success).value}")
}