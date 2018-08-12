package app.email4mobile.remote

import javax.inject.Inject

class RemoteData @Inject constructor(private val remoteService: RemoteService) {

    fun netCallGetEmail() = remoteService.netCallGetEmail()

    fun netCallGetEmailDetail(login: String) = remoteService.netCallEmailDetail(login)

    fun netCallSendNewEmail(name: String, email: String) = remoteService.addNewEmail(name, email)

    fun netCallDeleteEmailByID(id: Int) = remoteService.deteleEmailByID(id)

}