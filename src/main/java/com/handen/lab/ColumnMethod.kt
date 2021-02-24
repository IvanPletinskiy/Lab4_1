package com.handen.lab

class ColumnMethod : Method {
    override fun encode(text: String, key: String): Result<String> {
        val filtered = text.filter {
            (it in 'a'..'z') || (it in 'A'..'Z')
        }

        val indexes = key.toCharArray()
                .withIndex()
                .sortedBy {
                    it.value
                }.map {
                    it.index
                }

        val encoded = indexes.map {
            var text = ""
            for (i in it until filtered.length step key.length) {
                text += filtered[i]
            }
            text
        }.reduce { acc, list ->
            acc + list
        }

        return Result.Success(String(encoded.toCharArray()))
    }

    override fun decode(text: String, key: String): Result<String> {
        val filtered = text.filter {
            (it in 'a'..'z') || (it in 'A'..'Z')
        }

        val fullColumnsCount = filtered.length % key.length
        val columnLength = filtered.length / key.length

        var startIndex = 0

        val columns = Array(key.length) { "" }

        key.toCharArray()
                .withIndex()
                .sortedBy {
                    it.value
                }.forEach {
                    val takeCount = if (it.index < fullColumnsCount) {
                        columnLength + 1
                    } else {
                        columnLength
                    }

                    val endIndex = startIndex + takeCount

                    val columnText = filtered.substring(startIndex, endIndex)
                    startIndex = endIndex
                    columns[it.index] = columnText
                }

        val decoded = (filtered.indices).map {
            val columnIndex = it % key.length
            columns[columnIndex][it / key.length]
        }.toCharArray()

        return Result.Success(String(decoded))
    }

    override fun toString(): String {
        return "Столбцовый метод"
    }
}

fun main() {
    val columnMethod = ColumnMethod()
    val decodedText = "cryptographylove"
    val key = "key"
    println("Encode: ${(columnMethod.encode(decodedText, key) as Result.Success).value}")
    val encoded = "rtrhocpgpleyoayv"
    println("Decode: ${(columnMethod.decode(encoded, key) as Result.Success).value}")
}