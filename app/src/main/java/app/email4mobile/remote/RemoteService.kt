package app.email4mobile.remote

import app.email4mobile.model.User
import app.email4mobile.model.UserDetail
import io.reactivex.Observable
import retrofit2.http.*

interface RemoteService {

    @GET(RemoteContract.API_GET_USER)
    fun netCallGetEmail(): Observable<List<User>>

    @GET(RemoteContract.API_GET_USER_DETAIL)
    fun netCallEmailDetail(@Path("username") login: String): Observable<UserDetail>

    @FormUrlEncoded
    @POST("")
    fun addNewEmail(@Field("name") name: String,
                    @Field("email") email: String)

    @DELETE("")
    fun deteleEmailByID(@Path("id")emailID: Int)

}