package com.melq.firebasetest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.melq.firebasetest.model.user.User

class CustomAdapter(private val userList: MutableList<User>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    lateinit var listener: OnItemClickListener

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val box: View = view.findViewById(R.id.layout_box)
        val tvFirst: TextView = view.findViewById(R.id.tv_first)
        val tvLast: TextView = view.findViewById(R.id.tv_last)
        val tvBorn: TextView = view.findViewById(R.id.tv_born)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userData = userList[position]

        holder.tvFirst.text = userData.first
        holder.tvLast.text = userData.last
        holder.tvBorn.text = userData.born.toString()

        holder.box.setOnClickListener {
            listener.onItemClick(it, position, userData.id)
        }
    }

    override fun getItemCount(): Int = userList.size

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, clickedId: String)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}