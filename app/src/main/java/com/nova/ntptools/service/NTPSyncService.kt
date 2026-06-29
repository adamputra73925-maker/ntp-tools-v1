package com.nova.ntptools.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.nova.ntptools.utils.NotificationHelper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class NTPSyncService : Service() {

    private lateinit var notificationHelper: NotificationHelper

    override fun onCreate() {
        super.onCreate()
        notificationHelper = NotificationHelper(this)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        GlobalScope.launch {
            syncNTP()
        }
        return START_STICKY
    }

    private suspend fun syncNTP() {
        try {
            notificationHelper.showSyncNotification("Syncing...")
            
            // NTP Server
            val ntpServer = "pool.ntp.org"
            val address = InetAddress.getByName(ntpServer)
            
            val socket = DatagramSocket()
            socket.soTimeout = 5000
            
            // Create NTP request packet
            val request = ByteArray(48)
            request[0] = 0x1b.toByte() // LI, Version, Mode
            
            val packet = DatagramPacket(request, request.size, address, 123)
            socket.send(packet)
            
            // Receive response
            val response = ByteArray(48)
            val receivePacket = DatagramPacket(response, response.size)
            socket.receive(receivePacket)
            
            // Calculate NTP time
            val txTime = ((response[40].toLong() and 0xff shl 24)
                    or (response[41].toLong() and 0xff shl 16)
                    or (response[42].toLong() and 0xff shl 8)
                    or (response[43].toLong() and 0xff))
            
            socket.close()
            
            Log.d("NTPSync", "Sync berhasil: $txTime")
            notificationHelper.showSyncNotification("Sync berhasil!")
            
        } catch (e: Exception) {
            Log.e("NTPSync", "Sync gagal: ${e.message}")
            notificationHelper.showSyncNotification("Sync gagal!")
        }
        
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
