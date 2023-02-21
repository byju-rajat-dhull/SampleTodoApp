package com.example.myapplication.presentation.addtodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
//import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.myapplication.databinding.FragmentAddTodoBinding
//import com.example.myapplication.R
//import com.example.myapplication.databinding.FragmentTodoListBinding
//import com.example.myapplication.databinding.FragmentUpdateTodoBinding
import com.example.myapplication.db.TodoDatabase
import com.example.myapplication.db.TodoItem
import java.text.SimpleDateFormat
import java.util.*

class AddTodoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val binding:FragmentUpdateTodoBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_update_todo,container,false)
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

//        binding.lifecycleOwner = this
//        binding.updateTodoViewModel=updateTodoViewModel

        binding.button.setOnClickListener {
//            Toast.makeText(activity,"Add button clicked",Toast.LENGTH_SHORT).show()
            var title:String=binding.editTextTextPersonName.text.toString()
            var desc:String=binding.editTextTextMultiLine.text.toString()
//            Toast.makeText(activity,"Title: "+title+" Desc: "+desc,Toast.LENGTH_LONG).show()
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
//                view?.findNavController()
//                    ?.navigate(UpdateTodoFragmentDirections.actionUpdateTodoFragmentToTodoListFragment())
            }
        }
        addTodoViewModel.navigator.observe(viewLifecycleOwner, Observer {
            if(it==true){
                view?.findNavController()
                    ?.navigate(AddTodoFragmentDirections.actionAddTodoFragmentToTodoListFragment())
                addTodoViewModel.hasFinishedNav()
            }
        })

        return binding.root
    }

}