package com.example.myhealth.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhealth.R
import com.example.myhealth.databinding.FragmentItemListBinding
import com.example.myhealth.model.data.HealthData
import com.example.myhealth.view.adapter.MyHealthListAdapter
import com.example.myhealth.view.splash.SplashActivity
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import org.koin.android.ext.android.getKoin
import kotlin.coroutines.CoroutineContext

class HealthListFragment : Fragment(), CoroutineScope {

    lateinit var adapter: MyHealthListAdapter
    private lateinit var binding: FragmentItemListBinding

    lateinit var dataJob: Job
    private lateinit var errorJob: Job

    companion object {
        fun newInstance() = HealthListFragment()
        private const val FRAGMENT_TAG = "com.example.myhealth.view.enterdatafragment"
    }

    @ObsoleteCoroutinesApi
    val healthListModel: HealthListViewModel by lazy {
        ViewModelProvider(this, getKoin().get()).get(HealthListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, parent, false)
        setHasOptionsMenu(true)
        return view
    }

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentItemListBinding.bind(view)
        init()
    }

    private fun init() {
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        adapter = MyHealthListAdapter()
        binding.list.adapter = adapter
        binding.fab.setOnClickListener {
            healthListModel.toSaveData()
        }
        healthListModel.openDialogFragmentLiveData.observe(viewLifecycleOwner, {
            openDialogFragment()
        })
    }

    override fun onStop() {
        super.onStop()
        dataJob.cancel()
        errorJob.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_health_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> showLogoutDialog().let { true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun renderError(error: Throwable) {
        error.message?.let { showError(it) }
    }

    private fun showError(text: String) {
        Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
    }

    private fun renderData(data: List<HealthData>?) {
        data?.let { adapter.data = it }
    }

    @ObsoleteCoroutinesApi
    override fun onStart() {
        super.onStart()
        dataJob = launch {
            healthListModel.getViewState().consumeEach { renderData(it) }
        }
        errorJob = launch {
            healthListModel.getErrorChannel().consumeEach { renderError(it) }
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure?")
            .setPositiveButton("Yes") { _, _ ->
                logout()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun logout() {
        AuthUI.getInstance()
            .signOut(requireContext())
            .addOnCompleteListener {
                SplashActivity.start(requireContext())
                activity?.finish()
            }
    }

    private fun openDialogFragment() {
        val dialogFragment = EnterDataFragment.newInstance()
        dialogFragment.show(childFragmentManager, FRAGMENT_TAG)
    }

}