package com.stacksense

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Main Application class for StackSense.
 * Annotated with @HiltAndroidApp to enable Hilt dependency injection.
 */
@HiltAndroidApp
class StackSenseApplication : Application()
