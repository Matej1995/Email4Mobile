package app.email4mobile.remote

import app.email4mobile.model.Email
import io.reactivex.Observable
import retrofit2.http.*

interface RemoteService {


    @GET(RemoteContract.API_GET_Email)
    fun netCallGetEmail(): Observable<List<Email>>

    @GET(RemoteContract.API_GET_Email_Detail)
    fun netCallEmailDetail(@Path("username") login: String): Observable<Email>

    @FormUrlEncoded
    @POST("")
    fun addNewEmail(@Field("name") name: String,
                    @Field("email") email: String)

    @DELETE("")
    fun deteleEmailByID(@Path("id")emailID: Int)


}