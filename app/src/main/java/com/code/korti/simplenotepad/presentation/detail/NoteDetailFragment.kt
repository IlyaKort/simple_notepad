package com.code.korti.simplenotepad.presentation.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.code.korti.simplenotepad.R
import com.code.korti.simplenotepad.databinding.FragmentNoteBinding
import com.code.korti.simplenotepad.utils.textChangedFlow
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class NoteDetailFragment: Fragment(R.layout.fragment_note) {

    private val viewModel by viewModels<NoteDetailViewModel>()

    private val binding: FragmentNoteBinding by viewBinding(FragmentNoteBinding::bind)

    private val args: NoteDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)

        transition()
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateNote(titleFlow(),bodyFlow(), args.idNote) { title, body, time ->
            binding.titleEditText.setText(title, TextView.BufferType.EDITABLE)
            binding.bodyEditText.setText(body, TextView.BufferType.EDITABLE)
            binding.timeTextView.text = convertStringToTime(time)
        }

        bindViewModel()

    }

    private fun convertStringToTime(stamp: Long): String {
        return  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val date = Instant.ofEpochSecond(stamp / 1000)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")
                .withZone(ZoneId.systemDefault())
            formatter.format(date)

        } else {

            val calendarDate = Calendar.getInstance()
            calendarDate.timeInMillis = stamp
            val format = SimpleDateFormat("HH:mm dd.MM.yyyy")
            format.format(calendarDate.time)

        }
    }

    private fun transition(){
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragmentContainerView
            duration = 300L
            scrimColor = Color.TRANSPARENT
        }
    }

    private fun titleFlow(): Flow<String> {
        return binding.titleEditText.textChangedFlow()
    }

    private fun bodyFlow(): Flow<String> {
        return binding.bodyEditText.textChangedFlow()
    }

    private fun bindViewModel() {
        viewModel.saveSuccessLiveData.observe(viewLifecycleOwner) { findNavController().navigateUp() }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.jobCancel()
    }
}