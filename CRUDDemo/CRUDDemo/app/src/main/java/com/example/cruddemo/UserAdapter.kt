package com.example.cruddemo

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var userList: ArrayList<UserModel> = ArrayList()
    private var onClickItem: ((UserModel) -> Unit)? = null
    private var onClickDeleteItem: ((UserModel) -> Unit)? = null

    fun addItems(items: ArrayList<UserModel>) {
        this.userList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (UserModel) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (UserModel) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items, parent, false)
    )

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bindView(user)
        holder.itemView.setOnClickListener { onClickItem?.invoke(user) }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(user) }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        private var contact = view.findViewById<TextView>(R.id.tvContact)
        private var address = view.findViewById<TextView>(R.id.tvAddress)
        var btnDelete = view.findViewById<Button>(R.id.btnDel)

        fun bindView(user: UserModel) {
            id.text = user.id.toString()
            name.text = user.name
            email.text = user.email
            contact.text = user.contact
            address.text = user.address
        }
    }
}

