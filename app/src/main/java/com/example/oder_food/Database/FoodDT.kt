package com.example.oder_food_app.DataBase

import android.os.Parcel
import android.os.Parcelable

data class FoodDT(var title: String, var price: Float, var img: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeFloat(price)
        parcel.writeString(img)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodDT> {
        override fun createFromParcel(parcel: Parcel): FoodDT {
            return FoodDT(parcel)
        }

        override fun newArray(size: Int): Array<FoodDT?> {
            return arrayOfNulls(size)
        }
    }
}


