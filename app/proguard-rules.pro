# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep Hilt generated code
-keepclassmembers,allowobfuscation class * {
    @dagger.hilt.android.lifecycle.HiltViewModel <init>(...);
}

# Keep data classes for serialization
-keep class com.stacksense.data.model.** { *; }
-keep class com.stacksense.data.analyzer.LibraryInfo { *; }

# Room database
-keep class com.stacksense.data.local.** { *; }
-keepclassmembers class * extends androidx.room.RoomDatabase {
    public static ** getDatabase(...);
}

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
