package com.melq.firebasetest.page.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.melq.firebasetest.R
import com.melq.firebasetest.databinding.FragmentCreateUserBinding

class CreateUserFragment : Fragment(R.layout.fragment_create_user) {
    private val vm: CreateUserViewModel by viewModels()

    private var _binding: FragmentCreateUserBinding? = null
    private val binding: FragmentCreateUserBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = FragmentCreateUserBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}