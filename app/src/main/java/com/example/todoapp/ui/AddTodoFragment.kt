package com.example.todoapp.ui

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.TextView
import com.example.todoapp.R
import com.example.todoapp.clearTime
import com.example.todoapp.database.MyDataBase
import com.example.todoapp.database.model.Todo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout

class AddTodoFragment:BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_todo_fragment,container,false)
    }

    lateinit var titleLayout :TextInputLayout
    lateinit var description: TextInputLayout
    lateinit var chooseDate: TextView
    lateinit var addButton : ImageButton
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        titleLayout = requireView().findViewById(R.id.title_layout)
        description = requireView().findViewById(R.id.description_layout)
        chooseDate = requireView().findViewById(R.id.date_picker)
        addButton = requireView().findViewById(R.id.add_todo_button)
        chooseDate.setText(""+calender.get(Calendar.DAY_OF_MONTH)+"/"+
            calender.get(Calendar.MONTH)+1+"/"+
            calender.get(Calendar.YEAR))
        chooseDate.setOnClickListener {
            showDatePicker()
        }
        addButton.setOnClickListener {
               if(validateForm()){
                   val title:String = titleLayout.editText?.text.toString()
                   val details:String = description.editText?.text.toString()
                      addTodoToDataBase(title,details)
               }
        }
    }

     fun addTodoToDataBase(title: String, details: String) {
           val todo = Todo(title=title, descrption = details, date = calender.clearTime().time)
         Log.e("date",calender.time.toString())
         MyDataBase.getInstance(requireContext().applicationContext).todoDao().addTodo(todo)
         onTodoAddedListener?.onTodoAdded()
         dismiss()
    }
    var onTodoAddedListener :OnTodoAddedListener? = null
    interface OnTodoAddedListener{
        fun onTodoAdded()
    }

    fun validateForm():Boolean{
        var isValid = true
        if (titleLayout.editText?.text.toString().isBlank()){
            titleLayout.error = "please enter todo title"
            isValid = false
        }else{
            titleLayout.error = null
            isValid = true
        }
        if (description.editText?.text.toString().isBlank()){
            description.error = "please enter todo description"
            isValid = false
        }else{
            description.error = null
            isValid = true
        }
        return isValid
    }
    var calender :java.util.Calendar = java.util.Calendar.getInstance()
    fun showDatePicker(){

        val datePicker = DatePickerDialog(requireContext(),object :DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                chooseDate.setText(""+dayOfMonth+"/"+(month+1)+"/"+year)
                calender.set(java.util.Calendar.YEAR,year)
                calender.set(java.util.Calendar.MONTH,month)
                calender.set(java.util.Calendar.DAY_OF_MONTH,dayOfMonth)
            }
        },  calender.get(java.util.Calendar.YEAR),
            calender.get(java.util.Calendar.MONTH),
            calender.get(java.util.Calendar.DAY_OF_MONTH)
        )
    datePicker.show()
    }
}
