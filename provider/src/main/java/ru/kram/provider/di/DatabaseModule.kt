package ru.kram.provider.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope
import org.koin.dsl.module
import ru.kram.provider.data.database.UserDatabase
import ru.kram.provider.data.database.mapper.UserContentValuesToUserDbMapper

val databaseModule = module {
	single { createUserDatabase(androidContext()) }
	single { UserContentValuesToUserDbMapper() }
}

private fun createUserDatabase(context: Context): UserDatabase {
	val TAG = "UserDatabase"
	return Room.databaseBuilder(context, UserDatabase::class.java, UserDatabase.DATABASE_NAME)
		.addCallback(object : RoomDatabase.Callback() {
			override fun onCreate(db: SupportSQLiteDatabase) {
				super.onCreate(db)
				Log.d(TAG, "onCreate")
			}

			override fun onOpen(db: SupportSQLiteDatabase) {
				super.onOpen(db)
				Log.d(TAG, "onOpen")
			}

			override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
				super.onDestructiveMigration(db)
				Log.d(TAG, "onDestructiveMigration")
			}
		})
		.fallbackToDestructiveMigration()
		.build()
}