package com.stacksense.di

import android.content.Context
import android.content.pm.PackageManager
import androidx.room.Room
import com.stacksense.data.analyzer.ApkAnalyzer
import com.stacksense.data.local.*
import com.stacksense.data.repository.*
import com.stacksense.data.export.BatchExportManager
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

    @Provides
    @Singleton
    fun provideFavoriteDao(database: StackSenseDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideCollectionDao(database: StackSenseDatabase): CollectionDao {
        return database.collectionDao()
    }

    @Provides
    @Singleton
    fun provideFavoritesRepository(favoriteDao: FavoriteDao): FavoritesRepository {
        return FavoritesRepository(favoriteDao)
    }

    @Provides
    @Singleton
    fun provideBatchExportManager(): BatchExportManager {
        return BatchExportManager()
    }
}
