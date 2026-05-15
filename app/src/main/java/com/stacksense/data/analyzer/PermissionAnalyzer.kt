package com.stacksense.data.analyzer

import com.stacksense.data.model.PermissionDetail
import com.stacksense.data.model.RiskLevel

object PermissionAnalyzer {
    fun analyzePermission(permissionName: String): PermissionDetail {
        return PermissionDatabase.PERMISSIONS[permissionName] ?: PermissionDetail(
            permissionName,
            RiskLevel.NORMAL,
            "No description available."
        )
    }

    fun calculatePrivacyScore(permissions: List<String>): Int {
        if (permissions.isEmpty()) return 100
        var score = 100
        permissions.forEach { perm ->
            val detail = analyzePermission(perm)
            when (detail.riskLevel) {
                RiskLevel.HIGH -> score -= 10
                RiskLevel.MEDIUM -> score -= 5
                RiskLevel.LOW -> score -= 2
                RiskLevel.NORMAL -> score -= 0
            }
        }
        return score.coerceAtLeast(0)
    }
}
