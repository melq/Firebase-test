package com.melq.firebasetest.page.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.melq.firebasetest.ActivityViewModel
import com.melq.firebasetest.R
import com.melq.firebasetest.databinding.FragmentCreateUserBinding

class CreateUserFragment : Fragment(R.layout.fragment_create_user) {
    private val vm: ActivityViewModel by activityViewModels()

    private var _binding: FragmentCreateUserBinding? = null
    private val binding: FragmentCreateUserBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = FragmentCreateUserBinding.bind(view)

        binding.tvOk.setOnClickListener {
            vm.createUser(
                binding.etUserName.text.toString(),
                binding.etFirstName.text.toString(),
                binding.etLastName.text.toString(),
                binding.etBorn.text.toString()
            )
            vm.eMessage.observe(viewLifecycleOwner) { msg ->
                if (msg.isNotEmpty()) {
                    Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
                    vm.eMessage.value = ""
                }
            }
            vm.done.observe(viewLifecycleOwner) {
                if (it == true) {
                    vm.done.value = false
                    findNavController().popBackStack()
                }
            }
        }

        binding.tvCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}