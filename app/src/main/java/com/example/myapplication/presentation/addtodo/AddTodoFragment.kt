package com.example.myapplication.presentation.addtodo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.myapplication.databinding.FragmentAddTodoBinding
import com.example.myapplication.data.db.TodoDatabase
import com.example.myapplication.data.db.TodoItem
import java.text.SimpleDateFormat
import java.util.*

class AddTodoFragment : Fragment() {

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddTodoBinding.inflate(inflater,container, false)
        val application= requireNotNull(this.activity).application
        // Create an instance of the ViewModel Factory.
        val dataSource = TodoDatabase.getInstance(application).todoDatabaseDao
        val viewModelFactory = AddTodoViewModelFactory(dataSource, application)

// Get a reference to the ViewModel associated with this fragment.
        val addTodoViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[AddTodoViewModel::class.java]

        binding.button.setOnClickListener {
            var title:String=binding.editTextTextPersonName.text.toString()
            var desc:String=binding.editTextTextMultiLine.text.toString()
            if(title==""){
                Toast.makeText(activity,"Please add title",Toast.LENGTH_SHORT).show()
            }
            else if(desc==""){
                Toast.makeText(activity,"Please add Description",Toast.LENGTH_SHORT).show()
            }
            else {
                var todoItem = TodoItem()
                todoItem.title = title
                todoItem.description = desc
                val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                val currentDateAndTime: String = sdf.format(Date())
                todoItem.timestamp="Last Updated: "+currentDateAndTime
                addTodoViewModel.addTodo(todoItem)
                view?.findNavController()
                    ?.navigate(AddTodoFragmentDirections.actionAddTodoFragmentToTodoListFragment())
            }
        }
//        addTodoViewModel.navigator.observe(viewLifecycleOwner, Observer {
//            if(it==true){
//                view?.findNavController()
//                    ?.navigate(AddTodoFragmentDirections.actionAddTodoFragmentToTodoListFragment())
//            }
//        })
        return binding.root
    }

}