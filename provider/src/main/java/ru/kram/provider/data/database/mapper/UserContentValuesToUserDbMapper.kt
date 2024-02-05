package ru.kram.provider.data.database.mapper

import android.content.ContentValues
import ru.kram.provider.data.database.entities.UserDb
import ru.kram.provider_contract.UserProviderApi

class UserContentValuesToUserDbMapper: (ContentValues) -> UserDb {
	override fun invoke(cv: ContentValues): UserDb {
		return UserDb(
			id = cv.getAsLong(UserProviderApi.Projection.ID),
			name = cv.getAsString(UserProviderApi.Projection.NAME),
			surname = cv.getAsString(UserProviderApi.Projection.SURNAME),
			age = cv.getAsInteger(UserProviderApi.Projection.AGE),
			height = cv.getAsInteger(UserProviderApi.Projection.HEIGHT),
			weight = cv.getAsInteger(UserProviderApi.Projection.WEIGHT),
			footSize = cv.getAsInteger(UserProviderApi.Projection.FOOT_SIZE)
		)
	}
}