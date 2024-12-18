package ru.kram.sandbox.common.contract_user_provider.model.user.mapper

import android.content.ContentValues
import ru.kram.sandbox.common.contract_user_provider.UserProviderApi
import ru.kram.sandbox.common.contract_user_provider.model.user.User

class UserToContentValueMapper: (User) -> ContentValues {

	override fun invoke(user: User): ContentValues {
		val cv = ContentValues()
		user.apply {
			cv.put(UserProviderApi.Projection.ID, id)
			cv.put(UserProviderApi.Projection.NAME, name)
			cv.put(UserProviderApi.Projection.SURNAME, surname)
			cv.put(UserProviderApi.Projection.AGE, age)
			cv.put(UserProviderApi.Projection.HEIGHT, height.value)
			cv.put(UserProviderApi.Projection.WEIGHT, weight.value)
			cv.put(UserProviderApi.Projection.FOOT_SIZE, footSize.value)
		}
		return cv
	}
}