package com.example.todoapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.database.model.Todo

class TodosRecyclerAdapter(var items : MutableList<Todo>?): RecyclerView.Adapter<TodosRecyclerAdapter.ViewHolder>() {


    class ViewHolder (itemView:View):RecyclerView.ViewHolder(itemView){
         val title:TextView = itemView.findViewById(R.id.todo_title_item_view)
        val description :TextView = itemView.findViewById(R.id.description_item_view)
        val doneButton : ImageButton = itemView.findViewById(R.id.check_done_button)
        val guide :View = itemView.findViewById(R.id.left_guide)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    fun changeData(newItems:MutableList<Todo>){
        items = newItems
        notifyDataSetChanged()
    }
    var onItemClickLitener:OnTodoClickListener? = null
    var onCheckButtonClickLitener:OnTodoClickListener?=null
    interface OnTodoClickListener{
        fun onItemClick(postion:Int,item:Todo)
    }
    fun itemColorChanger(holder: ViewHolder){
        holder.title.setTextColor(Color.GREEN)
        holder.guide.setBackgroundColor(Color.GREEN)
        holder.doneButton.setImageResource(R.drawable.ic_done)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val colorChanger = holder
        val item = items!!.get(position)
        holder.title.setText(item.title)
        holder.description.setText(item.descrption)

        holder.itemView.setOnClickListener {
            if(onItemClickLitener != null){
                onItemClickLitener?.onItemClick(position,item)
            }
        }
        holder.doneButton.setOnClickListener {
            if (onCheckButtonClickLitener != null){
                onCheckButtonClickLitener?.onItemClick(position,item)
                itemColorChanger(colorChanger)
            }
        }
        if (item.isDone == true){
            itemColorChanger(colorChanger)
        }

    }
}