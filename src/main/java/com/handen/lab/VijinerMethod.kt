package com.handen.lab

class VijinerMethod() : Method {

    val alphabet = arrayOf('А','Б','В','Г','Д','Е', 'Ё', 'Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х','Ц','Ч','Ш','Щ','Ъ','Ы','Ь','Э','Ю','Я')

    override fun encode(text: String, key: String): Result<String> {
        val upperKey = key.toUpperCase()
        val filtered = text.filter {
            (it in 'а'..'я') || (it in 'А'..'Я')
        }.toUpperCase().mapIndexed { index, textLetter ->
            val upperKeyLetterIndex = alphabet.indexOf(upperKey[index % upperKey.length])
            val upperKeyLetter = alphabet[(upperKeyLetterIndex + index / upperKey.length) % alphabet.size]
//            val upperKeyLetter = A + (upperKey[index % upperKey.length] - A + index / upperKey.length) % (RUSSIAN_LETTERS_COUNT - 1)
            getChar(upperKeyLetter, textLetter)
        }

        return Result.Success(String(filtered.toCharArray()))
    }

    override fun decode(text: String, key: String): Result<String> {
        val upperKey = key.toUpperCase()
        val filtered = text.filter {
            (it in 'а'..'я') || (it in 'А'..'Я')
        }.toUpperCase().mapIndexed { index, textLetter ->
//            val keyLetter = A + (key[index % key.length] - A + index / key.length) % (RUSSIAN_LETTERS_COUNT - 1)
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
//        return A + (keyLetter.toUpperCase() - A + textLetter.toUpperCase().toInt() - A.toInt()) % (RUSSIAN_LETTERS_COUNT - 1)
    }

    fun getSourceChar(keyLetter: Char, encodedLetter: Char): Char {
        val index = (encodedLetter + alphabet.size - keyLetter) % alphabet.size
//        val index = (encodedLetter + RUSSIAN_LETTERS_COUNT - keyLetter - 1) % (RUSSIAN_LETTERS_COUNT - 1)
//        return A + index
        return alphabet[index]
    }

    override fun toString(): String {
        return "Метод Виженера, прогрессивный ключ"
    }

    override val encodedPath: String
        get() = "C:\\ti\\vigenere\\encoded.txt"
    override val decodedPath: String
        get() = "C:\\ti\\vigenere\\decoded.txt"

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