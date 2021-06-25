package com.melq.firebasetest

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.melq.firebasetest.databinding.FragmentMainBinding
import com.melq.firebasetest.model.user.User

class MainFragment : Fragment(R.layout.fragment_main) {
    private val vm: ActivityViewModel by activityViewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    private lateinit var adapter: CustomAdapter

    override fun onStart() {
        super.onStart()
        val currentUser = vm.auth.currentUser
        if (currentUser != null) {
            vm.isLogin = true
            Log.d("MAIN_FRAGMENT", "email: ${currentUser.email}, uid: ${currentUser.uid}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        vm.loadUserList()

        adapter = CustomAdapter(vm.userList).apply { // adapterの作成
            setOnItemClickListener(object: CustomAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, clickedId: String) {
                    Log.d("MAIN_FRAGMENT", "item clicked: ${vm.userList[position]}")
                    vm.user = User(
                        vm.userList[position].id,
                        vm.userList[position].first,
                        vm.userList[position].last,
                        vm.userList[position].born)
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

        binding.fabLogin.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

        // ソート機能をつけたい
    }
}