package ru.kram.userprovider.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kram.userprovider.data.database.entities.UserEntity

@Database(
	entities = [UserEntity::class],
	version = 2
)
abstract class UserDatabase: RoomDatabase() {
	abstract fun userDao(): UserDao

	companion object {
		const val DATABASE_NAME = "users_database"
	}
}