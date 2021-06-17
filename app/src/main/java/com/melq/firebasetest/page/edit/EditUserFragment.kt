package com.melq.firebasetest.page.edit

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.melq.firebasetest.ActivityViewModel
import com.melq.firebasetest.R
import com.melq.firebasetest.databinding.FragmentEditUserBinding

class EditUserFragment : Fragment(R.layout.fragment_edit_user) {
    private lateinit var binding: FragmentEditUserBinding
    private val vm: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditUserBinding.inflate(inflater, container, false).also {
            it.vm = vm
            it.lifecycleOwner = this
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(context).apply {
                setTitle(R.string.delete)
                setMessage(R.string.ask_delete)
                setPositiveButton(R.string.ok) { _, _ ->
                    vm.deleteUser()
                    vm.done.observe(viewLifecycleOwner) {
                        if (it == true) {
                            vm.done.value = false
                            findNavController().popBackStack()
                        }
                    }
                }
                setNegativeButton(R.string.cancel) { _, _ -> /*なにもしない*/ }
            }
            dialog.show()
        }
    }
}