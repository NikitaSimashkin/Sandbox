package ru.kram.deathstar.deathstar_contract

import android.os.Parcel
import android.os.Parcelable

class DeathStar() : Parcelable {
	var width: Int = 0
	var height: Int = 0
	var weight: Int = 0
	var owner: String = ""

	constructor(parcel: Parcel) : this() {
		width = parcel.readInt()
		height = parcel.readInt()
		weight = parcel.readInt()
		owner = parcel.readString().orEmpty()
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeInt(width)
		parcel.writeInt(height)
		parcel.writeInt(weight)
		parcel.writeString(owner)
	}

	fun readFromParcel(inParcel: Parcel) {
		width = inParcel.readInt()
		height = inParcel.readInt()
		weight = inParcel.readInt()
		owner = inParcel.readString().orEmpty()
	}

	override fun describeContents(): Int {
		return 0
	}

	override fun toString(): String {
		return "DeathStar(width=$width, height=$height, weight=$weight, owner='$owner')"
	}

	companion object CREATOR : Parcelable.Creator<DeathStar> {
		override fun createFromParcel(parcel: Parcel): DeathStar {
			return DeathStar(parcel)
		}

		override fun newArray(size: Int): Array<DeathStar?> {
			return arrayOfNulls(size)
		}
	}
}