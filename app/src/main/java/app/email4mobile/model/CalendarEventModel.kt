package app.email4mobile.model

data class CalendarEventModel(
        var id: Long? = 0,
        var title: String = "",
        var location: String = "",
        var startTime: String = "", //Calendar
        var endTime: String = "", // Calendar
        var repeat: String = "",
        var alert: String = "",
        var important: String = "",
        var color: Int = 0
)
