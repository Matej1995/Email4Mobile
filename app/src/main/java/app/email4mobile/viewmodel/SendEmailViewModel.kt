package app.email4mobile.viewmodel

class SendEmailViewModel: BaseViewModel() {


    fun sendEmail(name: String, email: String){
        return repository.addNewEmail(name, email)
    }
}