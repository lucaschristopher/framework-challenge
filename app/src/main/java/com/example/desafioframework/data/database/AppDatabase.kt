package com.example.desafioframework.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.desafioframework.data.dao.AlbumDao
import com.example.desafioframework.data.dao.PostDao
import com.example.desafioframework.data.dao.ToDoDao
import com.example.desafioframework.data.model.Album
import com.example.desafioframework.data.model.Post
import com.example.desafioframework.data.model.ToDo

@Database(entities = [Post::class, Album::class, ToDo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val toDoDao: ToDoDao
    abstract val postDao: PostDao
    abstract val albumDao: AlbumDao

    companion object {
        // @Volatile indicates that this instance will always work in main memory, never via cache
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it, if it is, then create the database.
            // "synchronized" causes all concurrent database accesses to be queued and handled one by one.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "framework_db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}