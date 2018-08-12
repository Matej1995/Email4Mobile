package app.email4mobile.adapter

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import app.email4mobile.R
import app.email4mobile.databinding.ItemRowEmailBinding
import app.email4mobile.model.Email
import app.email4mobile.ui.activity.DetailEmail

class EmailAdapter(emailList: ArrayList<Email>): RecyclerView.Adapter<EmailAdapter.UViewHolder>() {

    private val emailList: MutableList<Email>? = emailList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var binding: ViewDataBinding? = null
        binding = DataBindingUtil.inflate(inflater, R.layout.item_row_email, parent, false)
        return UViewHolder(binding as ItemRowEmailBinding)
    }

    override fun getItemCount(): Int {
        return emailList!!.size
    }

    override fun onBindViewHolder(holder: UViewHolder, position: Int) {
        holder.bindItemm(emailList!![position])
        val email = emailList[position]

        holder.itemView.setOnClickListener {
            val i = Intent(holder.itemView.context, DetailEmail::class.java)
            i.putExtra("emailnName", email.name)
            i.putExtra("emailEmail", email.email)
            holder.itemView.context.startActivity(i)
        }


    }


    inner class UViewHolder : RecyclerView.ViewHolder {
        var itemRowUserBinding: ItemRowEmailBinding? = null

        internal constructor(binding: ItemRowEmailBinding): super(binding.root){
            this.itemRowUserBinding = binding
        }

        internal fun bindItemm(email: Email){
            itemRowUserBinding?.model = email
            itemRowUserBinding?.executePendingBindings()
        }

    }

}


