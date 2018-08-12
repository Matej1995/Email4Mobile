package app.email4mobile.data.email

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import app.email4mobile.data.email.entity.EmailEntity
import app.email4mobile.model.Email

@Database(entities = arrayOf(EmailEntity::class), version = 1)
abstract class RoomData : RoomDatabase() {

    abstract fun userDao(): EmailDao

    companion object {

        fun buildPersistentCurrency(context: Context):
                RoomData = Room.databaseBuilder(context.applicationContext,
                RoomData::class.java, RoomContract.appdb).build()

    }

}