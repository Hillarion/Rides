package com.thierrystpierre.rides.data.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Vehicle(
    val id : Int,
//    val uid :String,
    val vin :String,
    val make_and_model :String,
    val color:String,
//    val transmission :String,
//    val drive_type :String,
//    val fuel_type :String,
    val car_type :String,
//    val doors: Int,
//    val mileage : Int,
//    val kilometrage: Int,
//    val license_plate : String,
//    val specs: List<String>,
//    val car_options: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        vin = parcel.readString()?: "",
        make_and_model = parcel.readString()?: "",
        color = parcel.readString()?: "",
        car_type = parcel.readString()?: ""
    )
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(vin)
        dest.writeString(make_and_model)
        dest.writeString(color)
        dest.writeString(car_type)
    }

    companion object CREATOR : Parcelable.Creator<Vehicle> {
        override fun createFromParcel(parcel: Parcel): Vehicle {
            return Vehicle(parcel)
        }

        override fun newArray(size: Int): Array<Vehicle?> {
            return arrayOfNulls(size)
        }
    }
}

data class SortedVehicle(
    val vin :String,
    val make_and_model :String
)