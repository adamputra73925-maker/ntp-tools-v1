package com.nova.ntptools.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        "NTPTools",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USERNAME = "username"
        private const val KEY_DRAG_HS_VALUE = "drag_hs_value"
        private const val KEY_DPI_ENABLED = "dpi_enabled"
        private const val KEY_SHIZUKU_ENABLED = "shizuku_enabled"
    }

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        preferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }

    fun getUsername(): String {
        return preferences.getString(KEY_USERNAME, "") ?: ""
    }

    fun setUsername(username: String) {
        preferences.edit().putString(KEY_USERNAME, username).apply()
    }

    fun getDragHsValue(): Int {
        return preferences.getInt(KEY_DRAG_HS_VALUE, 0)
    }

    fun setDragHsValue(value: Int) {
        preferences.edit().putInt(KEY_DRAG_HS_VALUE, value).apply()
    }

    fun isDpiEnabled(): Boolean {
        return preferences.getBoolean(KEY_DPI_ENABLED, false)
    }

    fun setDpiEnabled(enabled: Boolean) {
        preferences.edit().putBoolean(KEY_DPI_ENABLED, enabled).apply()
    }

    fun isShizukuEnabled(): Boolean {
        return preferences.getBoolean(KEY_SHIZUKU_ENABLED, false)
    }

    fun setShizukuEnabled(enabled: Boolean) {
        preferences.edit().putBoolean(KEY_SHIZUKU_ENABLED, enabled).apply()
    }

    fun logout() {
        preferences.edit().clear().apply()
    }
}
