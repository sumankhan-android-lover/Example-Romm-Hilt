package com.webskitters.client.example_romm_hilt.di

import android.app.Application
import androidx.room.Room
import com.webskitters.client.example_romm_hilt.data.dao.ArticleDao
import com.webskitters.client.example_romm_hilt.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: AppDatabase.Callback): AppDatabase{
        return Room.databaseBuilder(application, AppDatabase::class.java, "news_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideArticleDao(db: AppDatabase): ArticleDao {
        return db.getArticleDao()
    }
}