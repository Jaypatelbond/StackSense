package com.stacksense.data.export

import com.stacksense.data.model.AppInfo
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BatchExportManager @Inject constructor() {
    fun exportBatch(apps: List<AppInfo>, format: String, outputDir: File): File? {
        val file = File(outputDir, "stacksense_export_${System.currentTimeMillis()}.$format")
        val success = when (format.lowercase()) {
            "csv" -> CsvExporter.exportToCsv(apps, file)
            "html" -> HtmlReportGenerator.generateHtmlReport(apps, file)
            else -> false
        }
        return if (success) file else null
    }
}
