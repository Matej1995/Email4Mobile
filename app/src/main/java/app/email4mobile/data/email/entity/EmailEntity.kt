package app.email4mobile.data.email.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "email")
data class EmailEntity(
        @PrimaryKey(autoGenerate = true) val iid: Int,
        var id: Int,
        var name: String,
        var email: String
)