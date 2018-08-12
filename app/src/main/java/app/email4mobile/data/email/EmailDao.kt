package app.email4mobile.data.email

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import app.email4mobile.data.email.entity.EmailEntity
import io.reactivex.Flowable

@Dao
interface EmailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(emailEntity: List<EmailEntity>)

    @Query(RoomContract.select_from_user)
    fun selectAll(): Flowable<List<EmailEntity>>

}