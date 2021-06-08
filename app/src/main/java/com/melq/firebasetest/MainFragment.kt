package com.melq.firebasetest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.melq.firebasetest.databinding.FragmentMainBinding
import com.melq.firebasetest.model.user.User
import com.melq.firebasetest.model.user.UserFireStore

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val vm: MainViewModel by viewModels()

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

        binding.mainButton.setOnClickListener {
            vm.changeText()
            UserFireStore().editUser(User("Ada", "Ada", "Lovelace", 1815))
        }
    }
}