package com.melq.firebasetest.page.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.melq.firebasetest.R
import com.melq.firebasetest.databinding.FragmentEditUserBinding

class EditUserFragment : Fragment(R.layout.fragment_edit_user) {
    private val vm: EditUserViewModel by viewModels()

    private var _binding: FragmentEditUserBinding? = null
    private val binding: FragmentEditUserBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentEditUserBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}