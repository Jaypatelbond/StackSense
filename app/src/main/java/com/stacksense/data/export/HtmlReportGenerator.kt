package com.stacksense.data.export

import com.stacksense.data.model.AppInfo
import java.io.File

object HtmlReportGenerator {
    fun generateHtmlReport(apps: List<AppInfo>, outputFile: File): Boolean {
        return try {
            outputFile.bufferedWriter().use { writer ->
                writer.write("<html><head><title>Report</title></head><body><h1>StackSense Report</h1></body></html>")
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}
