package com.handen.lab

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.*

private const val DEFAULT_PORT = 4555

fun sendNow(title: String, message: String, callback: OnSendNotificationCallback) {
    Thread {
        try {
            val lines = readArpTable()
            val mask = getNetworkMaskFromTable(lines)
            if (mask != null) {
                val broadcastAddress = getBroadcastAddress(mask)
                val packet = createDatagramPacket(title, message, broadcastAddress)
                val datagramSocket = createDatagramSocket()
                datagramSocket.send(packet)
            } else {
                callback.onFailure("Can't get subnet mask")
            }
        } catch (e: Exception) {
            callback.onFailure("Unknown error during notification delivery")
        }
    }.start()
}

fun sendAtTime(
    title: String,
    message: String,
    dateTime: Long,
    callback: OnSendNotificationCallback
) {
    Thread {
        while (dateTime > Date().time) {
            Thread.sleep(1)
        }
        sendNow(title, message, callback)
    }.start()
}

private fun readArpTable(): List<String> {
    val arpProcess = Runtime.getRuntime().exec("arp -a")
    val lines = arpProcess.inputStream.bufferedReader(charset("cp866")).readLines()
    arpProcess.destroy()
    return lines
}

private fun getNetworkMaskFromTable(lines: List<String>): String? {
    val dynamicTableEntries = lines.filter {
        it.contains("динамический")
    }
    val addresses = dynamicTableEntries.map {
        val array = it.trim().split("\\s+".toRegex())
        val ipAddress = array[0]
        ipAddress
    }

    return addresses.firstOrNull()
}

private fun getBroadcastAddress(mask: String): InetAddress {
    val address: InetAddress = InetAddress.getByName(mask)
    val a: ByteArray = address.address
    a[3] = 255.toByte()
    return InetAddress.getByAddress(a)
}

private fun createDatagramPacket(
    title: String,
    message: String,
    broadcastAddress: InetAddress
): DatagramPacket {
    val bytes = "$title\n$message".toByteArray(Charsets.UTF_16)
    val packet = DatagramPacket(bytes, bytes.size, broadcastAddress, DEFAULT_PORT)
    return packet
}

private fun createDatagramSocket(): DatagramSocket {
    val datagramSocket = DatagramSocket(DEFAULT_PORT)
    datagramSocket.broadcast = true
    datagramSocket.soTimeout = 5000
    return datagramSocket
}


interface OnSendNotificationCallback {
    fun onFailure(message: String)
}