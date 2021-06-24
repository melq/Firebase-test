package com.melq.firebasetest.page.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
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

        binding.btLogin.setOnClickListener {
            vm.loginPushed(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }

        binding.btCreate.setOnClickListener {
            vm.createPushed(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            vm.eMessage.observe(viewLifecycleOwner) { msg ->
                if (msg.isNotEmpty()) {
                    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
                    vm.eMessage.value = ""
                }
            }
            vm.done.observe(viewLifecycleOwner) {
                if (it == true) {
                    findNavController().popBackStack()
                    vm.done.value = false
                }
            }
        }
    }
}