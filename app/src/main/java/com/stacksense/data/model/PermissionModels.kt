package com.stacksense.data.model

enum class RiskLevel(val displayName: String, val color: Long) {
    HIGH("High Risk", 0xFFF44336),
    MEDIUM("Medium Risk", 0xFFFF9800),
    LOW("Low Risk", 0xFF4CAF50),
    NORMAL("Normal", 0xFF9E9E9E)
}

data class PermissionDetail(
    val permissionName: String,
    val riskLevel: RiskLevel,
    val description: String
)
