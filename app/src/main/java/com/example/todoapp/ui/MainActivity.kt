package com.example.todoapp.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var navigationView: BottomNavigationView
    lateinit var addTodoView : ImageView
    var list = List()
    var setting = Setting()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addTodoView = findViewById(R.id.add_button)
        addTodoView.setOnClickListener {
           showAddBottomSheet()
        }
        navigationView = findViewById(R.id.main_nav)
        navigationView.setOnItemSelectedListener {
            if (it.itemId == R.id.nav_list){
                pushFragment(list)
            }else if (it.itemId == R.id.nav_setting){
              pushFragment(setting)
            }
            return@setOnItemSelectedListener true
        }
        navigationView.selectedItemId= R.id.nav_list
    }

    private fun showAddBottomSheet() {

        val addBottomSheet = AddTodoFragment()
        addBottomSheet.show(supportFragmentManager,"")
        addBottomSheet.onTodoAddedListener = object :AddTodoFragment.OnTodoAddedListener{
            override fun onTodoAdded() {
                list.gettodosFromDataBase()
            }

        }
    }

    fun pushFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment)
            .commit()
    }
}