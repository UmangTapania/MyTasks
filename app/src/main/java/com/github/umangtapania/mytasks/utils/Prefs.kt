package com.github.umangtapania.mytasks.utils

import android.content.Context
import android.content.SharedPreferences
import kotlin.apply


class Prefs(context: Context) {
    init {
        sp = context.getSharedPreferences(Constants.MY_TASK_PREF, Context.MODE_PRIVATE)
        edit = sp.edit()
    }

    companion object {
        lateinit var sp: SharedPreferences
        lateinit var edit: SharedPreferences.Editor
    }

    fun setIsLogin() {
        sp.edit().putBoolean(Constants.IS_LOGIN, true).apply()
    }

    fun isLoginOnce(): Boolean {
        return sp.getBoolean(Constants.IS_LOGIN, false)
    }
}












