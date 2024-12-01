package ru.kram.userprovider.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.kram.userprovider.data.database.UserDatabase
import ru.kram.userprovider.data.database.mapper.UserContentValuesToUserDbMapper
import timber.log.Timber

val databaseModule = module {
	single { createUserDatabase(androidContext()) }
	single { UserContentValuesToUserDbMapper() }
}

private fun createUserDatabase(context: Context): UserDatabase {
	return Room.databaseBuilder(context, UserDatabase::class.java, UserDatabase.DATABASE_NAME)
		.addCallback(object : RoomDatabase.Callback() {
			override fun onCreate(db: SupportSQLiteDatabase) {
				super.onCreate(db)
				Timber.d("onCreate")
			}

			override fun onOpen(db: SupportSQLiteDatabase) {
				super.onOpen(db)
				Timber.d("onOpen")
			}

			override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
				super.onDestructiveMigration(db)
				Timber.d("onDestructiveMigration")
			}
		})
		.fallbackToDestructiveMigration()
		.build()
}