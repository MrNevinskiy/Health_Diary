package com.example.myhealth.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealth.R
import com.example.myhealth.databinding.FragmentItemListBinding
import com.example.myhealth.model.AppState
import com.example.myhealth.model.data.HealthData
import com.example.myhealth.view.adapter.MyHealthListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HealthListFragment : Fragment(R.layout.fragment_item_list) {

    private val viewModel: HealthListViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentItemListBinding
    private var id: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentItemListBinding.bind(view)
        viewModel.subscribe().observe(viewLifecycleOwner, {
            renderData(it)
        })
        init()
    }

    private fun init(){
        recyclerView = binding.list
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_healthListFragment_to_enterDataFragment)
        }
    }

    private fun renderData(appState: AppState?) {
        when (appState) {
            is AppState.Success<*> -> {
                when (appState.data) {
                    is HealthData -> {
                        recyclerView.adapter = MyHealthListAdapter(appState.data as List<HealthData>)
                    }
                }
            }
            else -> Toast.makeText(context, "ErrorData", Toast.LENGTH_LONG).show()
        }
    }
}