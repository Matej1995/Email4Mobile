package app.email4mobile.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.graphics.Color
import java.util.*

@Entity(tableName = "eventsData")
data class CalendarEvent(@PrimaryKey(autoGenerate = true) var id: Long?,
                         @ColumnInfo(name = "title") var title: String,
                         @ColumnInfo(name = "location") var location: String,
                         @ColumnInfo(name = "startTime") var startTime: Calendar,
                         @ColumnInfo(name = "endTime") var endTime: Calendar,
                         @ColumnInfo(name = "repeat") var repeat: String,
                         @ColumnInfo(name = "alert") var alert: String,
                         @ColumnInfo(name = "important") var important: String,
                         @ColumnInfo(name = "color") var color: Color
                            )



