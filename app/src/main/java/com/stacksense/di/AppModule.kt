package com.stacksense.di

import android.content.Context
import android.content.pm.PackageManager
import androidx.room.Room
import com.stacksense.data.analyzer.ApkAnalyzer
import com.stacksense.data.local.ScannedAppDao
import com.stacksense.data.local.StatsDao
import com.stacksense.data.local.StackSenseDatabase
import com.stacksense.data.repository.AppRepository
import com.stacksense.data.repository.StatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

    @Provides
    @Singleton
    fun provideStatsDao(database: StackSenseDatabase): StatsDao {
        return database.statsDao()
    }

    @Provides
    @Singleton
    fun provideStatsRepository(statsDao: StatsDao): StatsRepository {
        return StatsRepository(statsDao)
    }
}
