package com.nova.ntptools.utils

import android.content.Context
import android.provider.Settings

class DpiHelper(private val context: Context) {

    fun setDpi(dpi: Int): Boolean {
        return try {
            // Attempt to set DPI using system settings
            // Note: This requires root or Shizuku permission
            val displayDpi = dpi * 160 / 160 // Convert to display metrics
            Settings.Secure.putInt(
                context.contentResolver,
                Settings.Secure.DISPLAY_DENSITY_FORCED,
                dpi
            )
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getCurrentDpi(): Int {
        return try {
            Settings.Secure.getInt(
                context.contentResolver,
                Settings.Secure.DISPLAY_DENSITY_FORCED,
                160
            )
        } catch (e: Exception) {
            160
        }
    }
}
