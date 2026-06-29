package com.nova.ntptools.utils

import android.content.Context
import android.content.pm.PackageManager

class ShizukuHelper(private val context: Context) {

    fun isShizukuInstalled(): Boolean {
        return try {
            context.packageManager.getApplicationInfo("moe.shizuku.manager", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun requestShizukuPermission() {
        // Permission handling is done via Intent in MainActivity
    }
}
