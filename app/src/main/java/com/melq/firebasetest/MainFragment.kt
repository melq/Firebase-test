package com.melq.firebasetest

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.melq.firebasetest.databinding.FragmentMainBinding
import kotlinx.coroutines.*

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val vm: MainViewModel by viewModels()

    private lateinit var adapter: CustomAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        vm.loadUserList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.vm = vm
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CustomAdapter(vm.userList)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        context?.getDrawable(R.drawable.divider)?.let { dividerItemDecoration.setDrawable(it) }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        adapter.setOnItemClickListener(object: CustomAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, clickedId: String) {
                Log.d("MAIN_FRAGMENT", "item clicked: ${vm.userList[position]}")
                // Click時の遷移などの挙動を記述
            }
        } )

        // 初回起動以外はローカルコピーかローカルDBを参照したい
        vm.isUserListLoaded.observe(viewLifecycleOwner) {
            if (it == true) {
                adapter.notifyDataSetChanged()
                vm.isUserListLoaded.value = false
            }
        }

        binding.fabAdd.setOnClickListener {
            Log.d("MAIN_FRAGMENT", "fabAdd clicked")
            findNavController().navigate(R.id.action_mainFragment_to_createUserFragment)
        }

        binding.fabRefresh.setOnClickListener {
            Log.d("MAIN_FRAGMENT", "fabRefresh clicked")
            vm.loadUserList()
            adapter.notifyDataSetChanged()
        }

        // ソート機能をつけたい
    }
}