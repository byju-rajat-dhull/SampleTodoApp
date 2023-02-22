package com.example.myapplication.presentation.updatetodo

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.myapplication.databinding.FragmentUpdateTodoBinding
import com.example.myapplication.db.TodoDatabase
import com.example.myapplication.db.TodoItem
import java.text.SimpleDateFormat
import java.util.*

class UpdateTodoFragment : Fragment() {

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentUpdateTodoBinding.inflate(inflater,container,false)
        val application= requireNotNull(this.activity).application
        // Create an instance of the ViewModel Factory.
        val dataSource = TodoDatabase.getInstance(application).todoDatabaseDao
        val viewModelFactory = UpdateTodoViewModelFactory(dataSource, application)

        val updateTodoViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[UpdateTodoViewModel::class.java]


        val args= arguments?.let { UpdateTodoFragmentArgs.fromBundle(it) }
        if (args != null) {
            updateTodoViewModel.getArgs(args.id)
        }

        updateTodoViewModel.nightId.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                binding.titleET.setText(it.title)
                binding.descET.setText(it.description)
            }
        })

        binding.button.setOnClickListener {
            val title:String=binding.titleET.text.toString()
            val desc:String=binding.descET.text.toString()
            if(title==""){
                Toast.makeText(activity,"Please add title", Toast.LENGTH_SHORT).show()
            }
            else if(desc==""){
                Toast.makeText(activity,"Please add Description, it can't be empty", Toast.LENGTH_SHORT).show()
            }
            else {
                var currID:Long=-1
                if(args!=null){
                    currID=args.id
                }
                val currtitle:String = title
                val description:String = desc
                val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                val currentDateAndTime: String = sdf.format(Date())

                val timestamp:String = "Last Updated: $currentDateAndTime"
                updateTodoViewModel.updateTodo(TodoItem(currID,currtitle,description,timestamp))
            }
        }

        updateTodoViewModel.navigator.observe(viewLifecycleOwner, Observer {
            if(it==true){
                view?.findNavController()?.navigate(UpdateTodoFragmentDirections.actionUpdateTodoFragmentToTodoListFragment())
                updateTodoViewModel.hasFinishedNav()
            }
        })

        return binding.root
    }

}