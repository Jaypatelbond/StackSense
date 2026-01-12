package com.stacksense.di

import android.content.Context
import android.content.pm.PackageManager
import androidx.room.Room
import com.stacksense.data.analyzer.ApkAnalyzer
import com.stacksense.data.local.ScannedAppDao
import com.stacksense.data.local.StackSenseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module providing application-level dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePackageManager(@ApplicationContext context: Context): PackageManager {
        return context.packageManager
    }

    @Provides
    @Singleton
    fun provideApkAnalyzer(): ApkAnalyzer {
        return ApkAnalyzer()
    }
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): StackSenseDatabase {
        return Room.databaseBuilder(
            context,
            StackSenseDatabase::class.java,
            StackSenseDatabase.DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()
    }
    
    @Provides
    @Singleton
    fun provideScannedAppDao(database: StackSenseDatabase): ScannedAppDao {
        return database.scannedAppDao()
    }
}
