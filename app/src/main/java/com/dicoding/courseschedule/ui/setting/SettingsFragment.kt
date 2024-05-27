package com.dicoding.courseschedule.ui.setting

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        val swicthTheme = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        swicthTheme?.setOnPreferenceChangeListener { _, value ->
            when (value) {
                getString(R.string.pref_dark_on) -> updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
                getString(R.string.pref_dark_off) -> updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
                else -> updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference

        val addReminder = DailyReminder()
        val deleteNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        deleteNotification?.setOnPreferenceChangeListener { _, value ->
            val isEnabled = value as Boolean
            if (isEnabled) {
                addReminder.setDailyReminder(requireContext())
            } else {
                addReminder.cancelAlarm(requireContext())
            }

            true
        }

    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true

    }
}