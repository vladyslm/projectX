package com.myapps.projectx.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.myapps.projectx.R
import com.myapps.projectx.data.inbox.InboxMessage
import com.myapps.projectx.data.inbox.InboxMessageViewModel
import com.myapps.projectx.fragments.InboxFragmentDirections
import java.sql.Timestamp

class InboxAdapter(inboxViewModel: InboxMessageViewModel) : RecyclerView.Adapter<InboxAdapter.ViewHolder>() {

    //    private lateinit var inboxMessageLayout: ConstraintLayout
    private var inboxViewModel: InboxMessageViewModel = inboxViewModel
    private var inboxList = emptyList<InboxMessage>()


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.inbox_message, parent, false)

        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = inboxList[position]
        var msg = currentItem.message
        if (msg.length > 270) {
            msg = msg.substring(0, 270).plus("...")
        }

        val timestamp = Timestamp(currentItem.date)

        holder.itemView.findViewById<TextView>(R.id.inboxMessage).text = msg

        holder.itemView.findViewById<TextView>(R.id.inboxMessageFrom).text = currentItem.from
        holder.itemView.findViewById<TextView>(R.id.inboxMessageDate).text = timestamp.toString()
        if (currentItem.isRead){
            holder.itemView.findViewById<ImageView>(R.id.inboxIsReadIndicator).visibility = View.GONE
        }

        holder.itemView.findViewById<ConstraintLayout>(R.id.inboxMessageLayout).setOnClickListener {


        val action =
                InboxFragmentDirections.navigateToMessage(
                    currentItem.message,
                    currentItem.from,
                    timestamp.toString()
                )

            inboxViewModel.markAsRead(currentItem.id)

            Navigation.findNavController(holder.itemView).navigate(action)

        }

    }

    override fun getItemCount(): Int {
        return inboxList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(inbox: List<InboxMessage>) {
        this.inboxList = inbox
        notifyDataSetChanged()
    }
}