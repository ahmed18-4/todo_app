package com.example.todoapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.adapters.TodosRecyclerAdapter
import com.example.todoapp.clearTime
import com.example.todoapp.const
import com.example.todoapp.database.MyDataBase
import com.example.todoapp.database.model.Todo
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }

    private fun initViews() {
        todosRecyler = requireView().findViewById(R.id.todos_recycler)
        calenderView = requireView().findViewById(R.id.calendarView)
        calenderView.selectedDate = CalendarDay.today()
        todosRecyler.adapter = adapter
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
}

private fun Intent.putExtra(todoItem: String, item: Todo) {

}


