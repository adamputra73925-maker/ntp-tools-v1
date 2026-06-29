package com.nova.ntptools.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nova.ntptools.R
import com.nova.ntptools.service.NTPSyncService
import com.nova.ntptools.utils.DpiHelper
import com.nova.ntptools.utils.NotificationHelper
import com.nova.ntptools.utils.PreferencesHelper
import com.nova.ntptools.utils.ShizukuHelper

class MainActivity : AppCompatActivity() {

    private lateinit var dragHsSeekBar: SeekBar
    private lateinit var dragHsValue: TextView
    private lateinit var dpiSwitch: Switch
    private lateinit var shizukuButton: Button
    private lateinit var syncButton: Button
    private lateinit var preferencesHelper: PreferencesHelper
    private lateinit var notificationHelper: NotificationHelper
    private lateinit var dpiHelper: DpiHelper
    private lateinit var shizukuHelper: ShizukuHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferencesHelper = PreferencesHelper(this)
        notificationHelper = NotificationHelper(this)
        dpiHelper = DpiHelper(this)
        shizukuHelper = ShizukuHelper(this)

        initializeViews()
        setupListeners()
        requestNotificationPermission()
    }

    private fun initializeViews() {
        dragHsSeekBar = findViewById(R.id.dragHsSeekBar)
        dragHsValue = findViewById(R.id.dragHsValue)
        dpiSwitch = findViewById(R.id.dpiSwitch)
        shizukuButton = findViewById(R.id.shizukuButton)
        syncButton = findViewById(R.id.syncButton)

        // Set initial values
        val savedHsValue = preferencesHelper.getDragHsValue()
        dragHsSeekBar.progress = savedHsValue
        updateDragHsDisplay(savedHsValue)

        val isDpiEnabled = preferencesHelper.isDpiEnabled()
        dpiSwitch.isChecked = isDpiEnabled
    }

    private fun setupListeners() {
        // Drag HS SeekBar
        dragHsSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateDragHsDisplay(progress)
                preferencesHelper.setDragHsValue(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // DPI Switch - Set to 520 when enabled
        dpiSwitch.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                // Try to set DPI to 520
                val result = dpiHelper.setDpi(520)
                if (result) {
                    Toast.makeText(this, "DPI set ke 520", Toast.LENGTH_SHORT).show()
                    preferencesHelper.setDpiEnabled(true)
                } else {
                    Toast.makeText(this, "Gagal mengatur DPI (membutuhkan root/Shizuku)", Toast.LENGTH_SHORT).show()
                    dpiSwitch.isChecked = false
                }
            } else {
                preferencesHelper.setDpiEnabled(false)
            }
        }

        // Shizuku Permission Button
        shizukuButton.setOnClickListener {
            requestShizukuPermission()
        }

        // Sync NTP Button
        syncButton.setOnClickListener {
            syncNtp()
        }
    }

    private fun updateDragHsDisplay(value: Int) {
        val percentage = (value / 100.0) * 20  // 20% max
        dragHsValue.text = String.format("Drag HS: %.1f%%", percentage)
    }

    private fun requestShizukuPermission() {
        try {
            val intent = Intent("moe.shizuku.manager.action.REQUEST_PERMISSION")
            intent.setPackage("moe.shizuku.manager")
            startActivity(intent)
            Toast.makeText(this, "Buka Shizuku untuk memberikan akses", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Shizuku tidak terinstall", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }
    }

    private fun syncNtp() {
        Toast.makeText(this, "Memulai sinkronisasi NTP...", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, NTPSyncService::class.java)
        startService(intent)
    }
}
