package com.example.todoapp.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.R
import com.example.todoapp.const
import com.example.todoapp.database.MyDataBase
import com.example.todoapp.database.model.Todo
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class UpdateTodoActivity : AppCompatActivity() {
    lateinit var titleEditLayout: TextInputLayout
    lateinit var description:TextInputLayout
    lateinit var editDoneButton : Button
    lateinit var datePickerTV : TextView
    lateinit var todoItem :Todo
    lateinit var calender :Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_todo)
       initViews()
        datePickerTV.setOnClickListener{
            showDatePicker()
        }
        editDoneButton.setOnClickListener {
            updateTodoInDataBase()
            if (validateForm()){
            backtToMainActivit()}
        }
    }

    private fun backtToMainActivit() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    private fun updateTodoInDataBase() {
        if (validateForm()){
            val newTitle:String = titleEditLayout.editText?.text.toString()
            val newDescrption:String = description.editText?.text.toString()
            addTodoToDataBase(newTitle,newDescrption)
        }
    }
    fun addTodoToDataBase(title: String, details: String) {
        todoItem.title = title
        todoItem.descrption= details
        todoItem.date= calender.time
        MyDataBase.getInstance(this.applicationContext).todoDao().updateTodo(todoItem)
    }


    fun initViews() {
        titleEditLayout = findViewById(R.id.title_layout_update)
        description = findViewById(R.id.description_layout_update)
        editDoneButton = findViewById(R.id.update_todo_done_button)
        datePickerTV = findViewById(R.id.date_picker_update_tv)

        var  todoId = intent.getIntExtra(const.TODO_ITEM, -1)
        Log.e("","$todoId")
        todoItem = MyDataBase.getInstance(this).todoDao().getTodoById(todoId)

        calender = Calendar.getInstance()
        calender.time = todoItem.date
        Log.e("current date","${calender.time}")
    }

    fun showDatePicker(){

        val datePicker = DatePickerDialog(this,object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                datePickerTV.setText(""+dayOfMonth+"/"+(month+1)+"/"+year)
                calender.set(Calendar.YEAR,year)
                calender.set(Calendar.MONTH,month)
                calender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            }
        },  calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }
    fun validateForm():Boolean{
        var isValid = true
        if (titleEditLayout.editText?.text.toString().isBlank()){
            titleEditLayout.error = "please enter todo title"
            isValid = false
        }else{
            titleEditLayout.error = null
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
}