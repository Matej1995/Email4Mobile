package app.email4mobile.data.email.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import app.email4mobile.data.email.RoomContract

@Entity(tableName = RoomContract.table_user)
data class EmailEntity(
        @PrimaryKey(autoGenerate = true) val iid: Int,
        var id: Int,
        var name: String,
        var email: String
)