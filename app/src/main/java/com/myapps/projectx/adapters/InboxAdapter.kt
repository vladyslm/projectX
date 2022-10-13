package com.myapps.projectx.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myapps.projectx.R
import com.myapps.projectx.data.inbox.InboxMessage

class InboxAdapter: RecyclerView.Adapter<InboxAdapter.ViewHolder>() {

    private var inboxList = emptyList<InboxMessage>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.inbox_message, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = inboxList[position]
        var msg = currentItem.message
        if (msg.length > 270){
            msg = msg.substring(0, 270).plus("...")
        }

        holder.itemView.findViewById<TextView>(R.id.inboxMessage).text = msg

        holder.itemView.findViewById<TextView>(R.id.inboxMessageFrom).text = currentItem.from
        holder.itemView.findViewById<TextView>(R.id.inboxMessageDate).text = currentItem.date
    }

    override fun getItemCount(): Int {
        return inboxList.size
    }

    fun setData(inbox: List<InboxMessage>){
        this.inboxList = inbox
        notifyDataSetChanged()
    }
}