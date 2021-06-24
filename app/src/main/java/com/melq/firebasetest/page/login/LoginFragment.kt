package com.melq.firebasetest.page.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.melq.firebasetest.ActivityViewModel
import com.melq.firebasetest.R
import com.melq.firebasetest.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    val vm: ActivityViewModel by activityViewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
    }
}