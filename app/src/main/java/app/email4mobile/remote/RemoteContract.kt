package app.email4mobile.remote

class RemoteContract {

    companion object {



        //Test
        const val API_GET_USER = "users?page=1&per_page=100"
        const val API_GET_USER_DETAIL = "users/{username}"

        //const val API_GET_Email = "users?format=json"

        const val API_GET_Email_Detail = "customers/{username}"
    }
}