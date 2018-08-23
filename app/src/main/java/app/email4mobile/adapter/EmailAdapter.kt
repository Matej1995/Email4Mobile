package app.email4mobile.adapter

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import app.email4mobile.R
import app.email4mobile.databinding.ItemRowEmailBinding
import app.email4mobile.model.User
import app.email4mobile.ui.activity.DetailEmail
import com.bumptech.glide.Glide

class EmailAdapter(emailList: ArrayList<User>): RecyclerView.Adapter<EmailAdapter.UViewHolder>() {

    private val userList: MutableList<User>? = emailList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var binding: ViewDataBinding? = null
        binding = DataBindingUtil.inflate(inflater, R.layout.item_row_email, parent, false)
        return UViewHolder(binding as ItemRowEmailBinding)
    }

    override fun getItemCount(): Int {
        return userList!!.size
    }

    override fun onBindViewHolder(holder: UViewHolder, position: Int) {
        holder.bindItemm(userList!![position])
        val user = userList[position]


        Glide.with(holder.itemView.context).load(user.avatar_url).preload()
        Glide.with(holder.itemView.context).load(user.avatar_url).into(holder.itemRowUserBinding?.avatar)

        holder.itemView.setOnClickListener {
            val i = Intent(holder.itemView.context, DetailEmail::class.java)
            i.putExtra("username", user.login)
            holder.itemView.context.startActivity(i)
        }
    }

    inner class UViewHolder : RecyclerView.ViewHolder {
        var itemRowUserBinding: ItemRowEmailBinding? = null

        internal constructor(binding: ItemRowEmailBinding): super(binding.root){
            this.itemRowUserBinding = binding
        }

        internal fun bindItemm(user: User){
            itemRowUserBinding?.model = user
            itemRowUserBinding?.executePendingBindings()
        }

    }
}


