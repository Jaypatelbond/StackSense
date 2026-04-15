package com.stacksense.data.export

import com.stacksense.data.model.AppInfo
import java.io.File

object CsvExporter {
    fun exportToCsv(apps: List<AppInfo>, outputFile: File): Boolean {
        return try {
            outputFile.bufferedWriter().use { writer ->
                writer.write("Package Name,App Name,Version Name,Version Code,Min SDK,Target SDK,Languages,Libraries Count\n")
                apps.forEach { app ->
                    val langs = app.languages.joinToString(";") { it.displayName }
                    writer.write("${app.packageName},${app.appName},${app.versionName},${app.versionCode},${app.minSdkVersion},${app.targetSdkVersion},\"$langs\",${app.libraries.size}\n")
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}
