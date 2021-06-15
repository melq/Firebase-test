package com.melq.firebasetest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.melq.firebasetest.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val vm: ActivityViewModel by activityViewModels()

    private lateinit var adapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm.loadUserList()
        binding = FragmentMainBinding.inflate(inflater, container, false).also {
            it.vm = vm
            it.lifecycleOwner = this
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CustomAdapter(vm.userList).apply { // adapterの作成
            setOnItemClickListener(object: CustomAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, clickedId: String) {
                    Log.d("MAIN_FRAGMENT", "item clicked: ${vm.userList[position]}")
                    vm.putUserData(vm.userList[position])
                    findNavController().navigate(R.id.action_mainFragment_to_editUserFragment)
                }
            } )
        }
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL) // dividerの作成
        context?.let { AppCompatResources.getDrawable(it, R.drawable.divider) }
        binding.recyclerView.also { // RecyclerViewの設定
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
            it.addItemDecoration(dividerItemDecoration)
        }

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