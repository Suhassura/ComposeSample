package com.compose.panthercompose.movies.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.compose.panthercompose.movies.data.models.Genre
import com.compose.panthercompose.movies.data.models.Movie
import com.compose.panthercompose.movies.utils.ListTypeConverter

@Database(entities = [Movie::class, Genre::class], version = 1, exportSchema = false)
@TypeConverters(ListTypeConverter::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun genreDao(): GenreDao

    companion object {
        private var instance: MoviesDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context.applicationContext).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MoviesDatabase::class.java, "movie_database.db"
            ).build()
    }
}