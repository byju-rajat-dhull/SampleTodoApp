package com.example.myapplication.presentation.listtodo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.db.TodoItem

class TodoAdapter(
    context: TodoListFragment,
    private val itemClick: (item: TodoItem) -> Unit,
): RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    var allItems = listOf<TodoItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false)

        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text=allItems[position].title
        holder.timeSt.text=allItems[position].timestamp



        holder.delIcon.setOnClickListener {
//            delBtnClick.onBtnClick(allItems[position])

        }

        holder.itemView.setOnClickListener {
            itemClick.invoke(allItems[position])
        }

    }

    override fun getItemCount(): Int {
        return allItems.size
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.textView3)
        val delIcon = itemView.findViewById<ImageView>(R.id.imageView)
        val timeSt=itemView.findViewById<TextView>(R.id.textView5)
    }
}

