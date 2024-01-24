package com.example.todoapp.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.adapters.TodosRecyclerAdapter
import com.example.todoapp.clearTime
import com.example.todoapp.const
import com.example.todoapp.database.MyDataBase
import com.example.todoapp.database.model.Todo
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import swipe.gestures.GestureManager
import java.util.Calendar



class List : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }
lateinit var todosRecyler:RecyclerView
lateinit var calenderView : MaterialCalendarView
 val adapter = TodosRecyclerAdapter(null)
    lateinit var placeHolder :LinearLayout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        placeHolder = view.findViewById(R.id.place_holder)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        gettodosFromDataBase()
    }
var calender = Calendar.getInstance()

     fun gettodosFromDataBase() {
         if (context==null) return
        var todosLsit = MyDataBase.getInstance(requireContext())
            .todoDao().getTodosByDate(calender.clearTime().time)
        adapter.changeData(todosLsit.toMutableList())
         placeHolder.isVisible = todosLsit.isEmpty()
    }

    private fun initViews() {
        todosRecyler = requireView().findViewById(R.id.todos_recycler)
        calenderView = requireView().findViewById(R.id.calendarView)
        calenderView.selectedDate = CalendarDay.today()
        todosRecyler.adapter = adapter

       initSwipeAdapter()

        var todo:Todo? = null
        adapter.onItemClickLitener = object :TodosRecyclerAdapter.OnTodoClickListener{
            override fun onItemClick(postion: Int, item: Todo) {
                val intent = Intent(requireContext(),UpdateTodoActivity::class.java)
                intent.putExtra(const.TODO_ITEM,item.id)
                startActivity(intent)
            }
        }
        adapter.onCheckButtonClickLitener = object :TodosRecyclerAdapter.OnTodoClickListener{
            override fun onItemClick(postion: Int, item: Todo) {
              item.isDone = true
                MyDataBase.getInstance(requireContext()).todoDao().updateTodo(item)


            }

        }
        calenderView.setOnDateChangedListener { widget, calenderDay, selected ->
            calender.set(Calendar.DAY_OF_MONTH,calenderDay.day)
            calender.set(Calendar.MONTH,calenderDay.month-1)
            calender.set(Calendar.YEAR,calenderDay.year)
            gettodosFromDataBase()
        }
    }

    private fun initSwipeAdapter() {
        var recyclerSwipeAdapter= GestureManager(leftCallback)

        recyclerSwipeAdapter.setIconLeft(ContextCompat.getDrawable(requireContext(),R.drawable.ic_delete))
        recyclerSwipeAdapter.setBackgroundColorLeft(ColorDrawable(Color.TRANSPARENT))
        recyclerSwipeAdapter.setIconSizeMultiplier(2)
        recyclerSwipeAdapter.setTextLeft("Remove")
        recyclerSwipeAdapter.setTextColor(Color.RED)
        var itemAttachHelper  = ItemTouchHelper(recyclerSwipeAdapter)
        itemAttachHelper.attachToRecyclerView(todosRecyler)
    }

    val    leftCallback : GestureManager.SwipeCallbackLeft = GestureManager.SwipeCallbackLeft {
     var deletedTodo : Todo?  = adapter.items?.get(it)
     if(deletedTodo != null)MyDataBase.getInstance(requireContext()).todoDao().deleteTodo(deletedTodo!!)
        adapter.notifyItemRemoved(it)
        adapter.notifyDataSetChanged()
        gettodosFromDataBase()
 }
}




