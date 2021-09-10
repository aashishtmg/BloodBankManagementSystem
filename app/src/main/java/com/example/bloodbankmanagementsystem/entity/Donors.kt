package com.example.bloodbankmanagementsystem.entity

import android.os.Parcel
import android.os.Parcelable


data class Donors (
    val _id:String? = null,
    val fullname: String? = null,
    val address: String? = null,
    val phone: String? = null,
    val Bgroup: String? = null,
    val age: String? = null
):Parcelable {
    var id: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(fullname)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeString(Bgroup)
        parcel.writeString(age)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Donors> {
        override fun createFromParcel(parcel: Parcel): Donors {
            return Donors(parcel)
        }

        override fun newArray(size: Int): Array<Donors?> {
            return arrayOfNulls(size)
        }
    }
}