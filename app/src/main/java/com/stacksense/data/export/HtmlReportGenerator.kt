package com.stacksense.data.export

import com.stacksense.data.model.AppInfo
import java.io.File

object HtmlReportGenerator {
    fun generateHtmlReport(apps: List<AppInfo>, outputFile: File): Boolean {
        return try {

            val totalApps = apps.size
            val analyzedApps = apps.count { it.isAnalyzed }

            val htmlBuilder = StringBuilder()
            htmlBuilder.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>StackSense Report</title>")
            htmlBuilder.append("<style>")
            htmlBuilder.append("body { font-family: 'Segoe UI', Roboto, sans-serif; background: #0f0c1b; color: #e2e8f0; margin: 40px; }")
            htmlBuilder.append("h1 { color: #8b5cf6; border-bottom: 2px solid #3b2e56; padding-bottom: 10px; }")
            htmlBuilder.append("table { width: 100%; border-collapse: collapse; margin-top: 20px; box-shadow: 0 4px 6px rgba(0,0,0,0.3); }")
            htmlBuilder.append("th { background: #1e1b4b; color: #a5b4fc; padding: 12px; text-align: left; }")
            htmlBuilder.append("td { background: #111827; padding: 12px; border-bottom: 1px solid #1f2937; }")
            htmlBuilder.append("tr:hover td { background: #1f2937; }")
            htmlBuilder.append("</style></head><body>")
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
