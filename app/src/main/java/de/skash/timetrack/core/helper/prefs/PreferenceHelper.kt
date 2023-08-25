package de.skash.timetrack.core.helper.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import de.skash.timetrack.core.model.User

private const val USER_DATA = "key_user_data"
private const val USER_TOKEN = "key_user_token"

private const val SHARED_PREFERENCE_NAME = "shared_prefs"

private val gson = Gson()

fun Context.getPrefs(): SharedPreferences {
    val masterKey = MasterKey.Builder(this)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    return EncryptedSharedPreferences.create(
        this,
        SHARED_PREFERENCE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

fun SharedPreferences.saveSelfUser(user: User) {
    val serializedUser = gson.toJson(user)

    edit()
        .putString(USER_DATA, serializedUser)
        .apply()
}

fun SharedPreferences.saveUserToken(token: String) {
    edit()
        .putString(USER_TOKEN, token)
        .apply()
}

fun SharedPreferences.getSelfUser(): User? {
    return try {
        gson.fromJson(getString(USER_DATA, ""), User::class.java)
    } catch (e: JsonSyntaxException) {
        null
    }
}

fun SharedPreferences.getUserToken(): String {
    return getString(USER_TOKEN, "") ?: ""
}
