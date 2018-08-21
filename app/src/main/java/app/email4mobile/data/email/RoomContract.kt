package app.email4mobile.data.email

class RoomContract {

    companion object {
        private const val select_from = "SELECT * FROM "
        const val appdb = "email.db"
        const val table_user = "email"
        const val select_from_user = select_from + table_user

        const val table_event = "event"
        const val select_from_event = select_from + table_event

    }

}