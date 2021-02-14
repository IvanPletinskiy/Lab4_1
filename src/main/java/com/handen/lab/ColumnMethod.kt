package com.handen.lab

class ColumnMethod : Method {
    override fun encode(text: String, key: String): String {
        val filtered = text.filter {
            (it in 'a'..'z') || (it in 'A'..'Z')
        }

        val width = key.length
        val indexes = key.toCharArray()
                .withIndex()
                .sortedBy {
                    it.value
                }.map {
                    it.index
                }

        val encoded = indexes.map {
            var text = ""
            for(i in it until filtered.length step key.length) {
                text += filtered[i]
            }
            text
        }.reduce { acc, list ->
            acc + list
        }

        return String(encoded.toCharArray())
    }

    override fun decode(text: String, key: String): String {
        return ""
    }
}

fun main() {
    val columnMethod = ColumnMethod()
    val decodedText = "cryptographylove"
    val key = "key"
    println("Encode: ${columnMethod.encode(decodedText, key)}")
}