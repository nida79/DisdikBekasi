package com.ekr.disdikbekasi.utils

import android.content.Context
import android.content.SharedPreferences
import hu.autsoft.krate.Krate
import hu.autsoft.krate.booleanPref
import hu.autsoft.krate.stringPref

class SessionManager(context: Context):Krate{
    private val PREFS_IS_LOGIN: String = "prefs_is_login"
    private val PREFS_FULL_NAME: String = "prefs_is_full_name"
    private val PREFS_ALAMAT: String = "prefs_is_alamat"
    private val PREFS_TOKEN : String = "prefs_is_token"
    private val PREFS_NO_HP : String = "prefs_is_no_hp"
    private val PREFS_FOTO : String = "prefs_is_foto"
    private val PREFS_ROLE : String = "prefs_is_role"
    private val PREFS_EMAIL : String = "prefs_is_email"
    private val PREFS_ID : String = "prefs_is_id"
    private val PREFS_STATUS : String = "prefs_is_status"
    private val PREFS_CUTI : String = "prefs_is_cuti"
    private val PREFS_AGAMA : String = "prefs_is_agama"
    private val PREFS_NIP : String = "prefs_is_nip"
    override val sharedPreferences: SharedPreferences = context.applicationContext
        .getSharedPreferences(
            "jularis_prefs", Context.MODE_PRIVATE
        )
    var prefIsLogin by booleanPref(PREFS_IS_LOGIN, false)
    var prefFullname by stringPref(PREFS_FULL_NAME, "")
    var prefToken by stringPref(PREFS_TOKEN, "")
    var prefAlamat by stringPref(PREFS_ALAMAT, "")
    var prefNohp by stringPref(PREFS_NO_HP, "")
    var prefFoto by stringPref(PREFS_FOTO,"")
    var prefRole by stringPref(PREFS_ROLE,"")
    var prefEmail by stringPref(PREFS_EMAIL,"")
    var prefStatusK by stringPref(PREFS_STATUS,"")
    var prefID by stringPref(PREFS_ID,"")
    var prefCuti by stringPref(PREFS_CUTI,"")
    var prefAgama by stringPref(PREFS_AGAMA,"")
    var prefNIP by stringPref(PREFS_NIP,"")

    fun logOut() {
        sharedPreferences.edit().clear().apply()
    }
}