package com.stacksense.data.analyzer

import com.stacksense.data.model.PermissionDetail
import com.stacksense.data.model.RiskLevel

object PermissionDatabase {
    val PERMISSIONS = mapOf(
        "android.permission.CAMERA" to PermissionDetail(
            "android.permission.CAMERA",
            RiskLevel.HIGH,
            "Allows the app to take pictures and record videos."
        ),
        "android.permission.READ_CONTACTS" to PermissionDetail(
            "android.permission.READ_CONTACTS",
            RiskLevel.HIGH,
            "Allows the app to read data about your contacts."
        ),
        "android.permission.ACCESS_FINE_LOCATION" to PermissionDetail(
            "android.permission.ACCESS_FINE_LOCATION",
            RiskLevel.HIGH,
            "Allows the app to get your precise location."
        ),
        "android.permission.INTERNET" to PermissionDetail(
            "android.permission.INTERNET",
            RiskLevel.NORMAL,
            "Allows the app to create network sockets."
        )
    )
}
