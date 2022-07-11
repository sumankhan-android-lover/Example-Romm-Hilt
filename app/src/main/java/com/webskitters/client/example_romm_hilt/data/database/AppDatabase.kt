package com.webskitters.client.example_romm_hilt.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.webskitters.client.example_romm_hilt.data.dao.ArticleDao
import com.webskitters.client.example_romm_hilt.data.model.article.Article
import com.webskitters.client.example_romm_hilt.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    class Callback @Inject constructor(
        private val database: Provider<AppDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}