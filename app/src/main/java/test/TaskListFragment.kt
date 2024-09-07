package test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_20240907.R

class TaskListFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)


        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        taskAdapter = TaskAdapter { task ->
            taskViewModel.delete(task)
        }
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Kết nối ViewModel


        taskViewModel.allTasks.observe(viewLifecycleOwner, Observer { tasks ->
            tasks?.let { taskAdapter.setTasks(it) }
        })

        // Thêm công việc mới
        view.findViewById<Button>(R.id.addTaskButton).setOnClickListener {
            val newTask = Task(title = "New Task", description = "Task Description")
            taskViewModel.insert(newTask)
        }
    }
}

