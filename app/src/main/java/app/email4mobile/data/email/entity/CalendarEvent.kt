package app.email4mobile.data.email.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Parcel
import android.os.Parcelable
import app.email4mobile.data.email.RoomContract

@Entity(tableName = RoomContract.table_event)
data class CalendarEvent(@PrimaryKey(autoGenerate = true) var id: Long?,
                         @ColumnInfo(name = "title") var title: String,
                         @ColumnInfo(name = "location") var location: String,
                         @ColumnInfo(name = "startTime") var startTime: String, //Calendar
                         @ColumnInfo(name = "endTime") var endTime: String, // Calendar
                         @ColumnInfo(name = "repeat") var repeat: String,
                         @ColumnInfo(name = "alert") var alert: String,
                         @ColumnInfo(name = "important") var important: String,
                         @ColumnInfo(name = "color") var color: Int
                            ): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(location)
        parcel.writeString(startTime)
        parcel.writeString(endTime)
        parcel.writeString(repeat)
        parcel.writeString(alert)
        parcel.writeString(important)
        parcel.writeInt(color)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CalendarEvent> {
        override fun createFromParcel(parcel: Parcel): CalendarEvent {
            return CalendarEvent(parcel)
        }

        override fun newArray(size: Int): Array<CalendarEvent?> {
            return arrayOfNulls(size)
        }
    }
}



