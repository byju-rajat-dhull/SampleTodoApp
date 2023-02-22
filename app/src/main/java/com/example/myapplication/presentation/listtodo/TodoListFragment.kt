package com.example.myapplication.presentation.listtodo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
//import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.myapplication.databinding.FragmentTodoListBinding
import com.example.myapplication.db.TodoDatabase


class TodoListFragment : Fragment() {

    lateinit var todoListViewModel: TodoListViewModel
    lateinit var binding:FragmentTodoListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater,container, false)
        setUpUi()
        return binding.root
    }

    fun setUpUi(){
        val application= requireNotNull(this.activity).application
//        // Create an instance of the ViewModel Factory.
        val dataSource = TodoDatabase.getInstance(application).todoDatabaseDao
        val viewModelFactory = TodoListViewModelFactory(dataSource, application)

        todoListViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[TodoListViewModel::class.java]


        //Click listener code will be here
        binding.floatingActionButton.setOnClickListener {
            view?.findNavController()?.navigate(TodoListFragmentDirections.actionTodoListFragmentToAddTodoFragment())
        }

        todoListViewModel.navigator.observe(viewLifecycleOwner, Observer{
            if(it==true) {
                //navigate
                view?.findNavController()?.navigate(TodoListFragmentDirections.actionTodoListFragmentToAddTodoFragment())
                todoListViewModel.hasFinishedNav()
            }
        })


        //Adapter Code
        val adapter= TodoAdapter(this, itemClick = {
                //implement it is now the TodoItem do whatever you want with it
            todoListViewModel.onTodoClicked(it.todoID)
        }, delClick = {
            todoListViewModel.onDelClicked(it)
        })


        todoListViewModel.updateId.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                view?.findNavController()?.navigate(
                    TodoListFragmentDirections.actionTodoListFragmentToUpdateTodoFragment(it)
                )
                todoListViewModel.onTodoNavigated()
            }
        })

        todoListViewModel.delId.observe(viewLifecycleOwner, Observer {
            if(it!=null){
//                Log.i("todolistFrag","observing started")
                todoListViewModel.delTodo(it)
            }
        })

        binding.recyclerView.adapter=adapter
        todoListViewModel.allTodos.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.allItems=it
            }
        })
    }
}

