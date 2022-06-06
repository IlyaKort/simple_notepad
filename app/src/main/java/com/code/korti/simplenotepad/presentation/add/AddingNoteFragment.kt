package com.code.korti.simplenotepad.presentation.add

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.code.korti.simplenotepad.R
import com.code.korti.simplenotepad.data.db.models.Color
import com.code.korti.simplenotepad.databinding.FragmentNoteBinding
import com.code.korti.simplenotepad.utils.textChangedFlow
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.flow.Flow

class AddingNoteFragment : Fragment(R.layout.fragment_note) {

    private val binding: FragmentNoteBinding by viewBinding(FragmentNoteBinding::bind)

    private val viewModel: AddingNoteViewModel by viewModels()

    private val noteId = getRandomNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.timeTextView.isVisible = false

        viewModel.bind(titleFlow(), bodyFlow(), noteId, Color.WHITE)
    }

    private fun titleFlow(): Flow<String> {
        return binding.titleEditText.textChangedFlow()
    }

    private fun bodyFlow(): Flow<String> {
        return binding.bodyEditText.textChangedFlow()
    }

    private fun getRandomNumber(): Long {
        return (0 until 1000000).random().toLong()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.jobCancel()
    }
}