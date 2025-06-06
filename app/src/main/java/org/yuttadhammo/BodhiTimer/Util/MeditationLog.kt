package org.yuttadhammo.BodhiTimer.Util

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import org.yuttadhammo.BodhiTimer.BodhiApp
import java.text.SimpleDateFormat
import java.util.*

object MeditationLog {
    private const val KEY_LOG = "meditation_log"

    private val prefs: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(BodhiApp.applicationContext())

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    fun logSession(duration: Int, time: Long = System.currentTimeMillis()) {
        if (duration < 10 * 60 * 1000) return
        logDate(time)
    }

    fun logDate(time: Long) {
        val set = prefs.getStringSet(KEY_LOG, mutableSetOf())!!.toMutableSet()
        set.add(dateFormat.format(Date(time)))
        prefs.edit().putStringSet(KEY_LOG, set).apply()
    }

    fun toggleDate(time: Long) {
        val set = prefs.getStringSet(KEY_LOG, mutableSetOf())!!.toMutableSet()
        val date = dateFormat.format(Date(time))
        if (set.contains(date)) set.remove(date) else set.add(date)
        prefs.edit().putStringSet(KEY_LOG, set).apply()
    }

    fun isLogged(time: Long): Boolean {
        val set = prefs.getStringSet(KEY_LOG, emptySet())
        val date = dateFormat.format(Date(time))
        return set?.contains(date) == true
    }

    fun allLoggedDates(): Set<String> = prefs.getStringSet(KEY_LOG, emptySet()) ?: emptySet()
}
