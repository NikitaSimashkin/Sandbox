package ru.kram.sandbox.biglist.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.kram.sandbox.biglist.domain.UserRepository
import ru.kram.sandbox.biglist.domain.model.User

class UserRepositoryImpl(
	private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {

	private val userFlow = MutableStateFlow(emptyList<User>())

	private var scope = CoroutineScope(dispatcher)

	private var idCounter = 0L

	override fun startLoadUsers() {
		scope = CoroutineScope(dispatcher)
		startCollectUsers()
	}

	override fun stopLoadUsers() {
		scope.cancel()
	}

	override fun getUsersFlow(): Flow<List<User>> {
		return userFlow
	}

	private fun startCollectUsers() {
		scope.launch {
			while(isActive) {
				if (userFlow.value.isEmpty()) {
					userFlow.value = generateUsers(20)
				}
				for (i in 0 until 1000) {
					delay(10000)
					userFlow.value = userFlow.value + generateUsers(1)
				}
			}
		}
	}

	private fun getRandomName(): String {
		val names = listOf(
			"James",
			"John",
			"Robert",
			"Michael",
			"William",
			"David",
			"Richard",
			"Joseph",
			"Thomas",
			"Charles",
			"Christopher",
			"Daniel",
			"Matthew",
			"Anthony",
			"Donald",
			"Mark",
			"Paul",
			"Steven",
			"Andrew",
			"Kenneth",
			"Joshua",
			"Kevin",
			"Brian",
			"George",
			"Edward",
			"Ronald",
			"Timothy",
			"Jason",
			"Jeffrey",
			"Ryan",
			"Jacob",
			"Gary",
			"Nicholas",
			"Eric",
			"Stephen",
			"Jonathan",
			"Larry",
			"Justin",
			"Scott",
			"Brandon"
		)
		return names.random()
	}

	private fun getRandomSurname(): String {
		return listOf(
			"Smith",
			"Johnson",
			"Williams",
			"Brown",
			"Jones",
			"Garcia",
			"Miller",
			"Davis",
			"Rodriguez",
			"Martinez",
			"Hernandez",
			"Lopez",
			"Gonzalez",
			"Wilson",
			"Anderson",
			"Thomas",
			"Taylor",
			"Moore",
			"Jackson",
			"Martin",
			"Lee",
			"Perez",
			"Thompson",
			"White",
			"Harris",
			"Sanchez",
			"Clark",
			"Ramirez",
			"Lewis",
			"Robinson",
			"Walker",
			"Young",
			"Allen",
			"King",
			"Wright",
			"Scott",
			"Torres",
			"Nguyen",
			"Hill",
			"Flores"
		).random()
	}

	private fun getRandomAge(): Int {
		return (18..99).random()
	}

	private fun generateUsers(number: Int): List<User> {
		return (0 until number).map {
			User(
				id = idCounter++,
				name = getRandomName(),
				surname = getRandomSurname(),
				age = getRandomAge(),
				avatarUrl = "https://source.unsplash.com/random/200x200?sig=$idCounter"
			)
		}
	}
}