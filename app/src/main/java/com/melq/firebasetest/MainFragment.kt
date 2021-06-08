package com.melq.firebasetest

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.melq.firebasetest.databinding.FragmentMainBinding
import com.melq.firebasetest.model.user.User
import com.melq.firebasetest.model.user.UserFireStore

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val vm: MainViewModel by viewModels()

    private var userList: MutableList<User> = UserFireStore().getAllUser()
    private val adapter = CustomAdapter(userList)

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

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        context?.getDrawable(R.drawable.divider)?.let { dividerItemDecoration.setDrawable(it) }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        adapter.setOnItemClickListener(object: CustomAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, clickedId: String) {
                Log.d("MAIN_FRAGMENT", "item clicked: ${userList[position]}")
            }
        } )

        binding.fabAdd.setOnClickListener {
            adapter.notifyDataSetChanged()
            vm.changeText()
            UserFireStore().editUser(User("Ada", "Ada", "Lovelace", 1815))
        }
    }
}