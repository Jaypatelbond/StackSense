package com.stacksense.data.export

import com.stacksense.data.model.AppInfo
import java.io.File

object HtmlReportGenerator {
    fun generateHtmlReport(apps: List<AppInfo>, outputFile: File): Boolean {
        return try {

            val totalApps = apps.size
            val analyzedApps = apps.count { it.isAnalyzed }
            val htmlBuilder = StringBuilder()
            htmlBuilder.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>StackSense Report</title></head><body>")
            htmlBuilder.append("<h1>StackSense Security &amp; Library Report</h1>")
            htmlBuilder.append("<h3>Summary: Total Apps: $totalApps | Analyzed Apps: $analyzedApps</h3>")
            htmlBuilder.append("<table border=\"1\"><tr><th>App Name</th><th>Package</th><th>Version</th><th>Analyzed</th></tr>")
            for (app in apps) {
                htmlBuilder.append("<tr><td>${app.appName}</td><td>${app.packageName}</td><td>${app.versionName}</td><td>${app.isAnalyzed}</td></tr>")
            }
            htmlBuilder.append("</table></body></html>")
            outputFile.bufferedWriter().use { writer ->
                writer.write(htmlBuilder.toString())
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}
