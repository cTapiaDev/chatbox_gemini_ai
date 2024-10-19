package cl.bootcamp.chatboxgeminiapi.room

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.bootcamp.chatboxgeminiapi.model.ChatModel

@Database(entities = [ChatModel::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun chatDao(): ChatDao
}