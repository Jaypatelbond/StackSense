package com.stacksense.data.export

import android.content.Context
import android.net.Uri
import com.stacksense.data.model.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

import dagger.hilt.android.qualifiers.ApplicationContext

@Singleton
class AnalysisExporter @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun exportAnalysisToUri(appInfo: AppInfo, uri: Uri) = withContext(Dispatchers.IO) {
        val json = createAppJson(appInfo)
        context.contentResolver.openOutputStream(uri)?.use { output ->
            output.write(json.toString(2).toByteArray())
        }
    }

    suspend fun saveApkToUri(appInfo: AppInfo, uri: Uri) = withContext(Dispatchers.IO) {
        val sourceFile = File(appInfo.apkPath)
        context.contentResolver.openOutputStream(uri)?.use { output ->
            FileInputStream(sourceFile).use { input ->
                input.copyTo(output)
            }
        }
    }

    private fun createAppJson(app: AppInfo): JSONObject {
        val json = JSONObject()
        json.put("packageName", app.packageName)
        json.put("appName", app.appName)
        json.put("versionName", app.versionName)
        json.put("versionCode", app.versionCode)
        json.put("apkSize", app.apkSize)
        json.put("installTime", formatTimestamp(app.installTime))
        json.put("updateTime", formatTimestamp(app.updateTime))
        json.put("isSystemApp", app.isSystemApp)

        val langsArray = JSONArray()
        app.languages.forEach { langsArray.put(it.displayName) }
        json.put("languages", langsArray)

        val libsArray = JSONArray()
        app.libraries.forEach { lib ->
            val libJson = JSONObject()
            libJson.put("name", lib.name)
            libJson.put("category", lib.category.displayName)
            libJson.put("packagePrefix", lib.packagePrefix)
            libsArray.put(libJson)
        }
        json.put("libraries", libsArray)

        val permsArray = JSONArray()
        app.permissions.forEach { permsArray.put(it) }
        json.put("permissions", permsArray)

        return json
    }

    private fun formatTimestamp(timestamp: Long): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date(timestamp))
    }
}
