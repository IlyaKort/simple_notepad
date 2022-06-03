package com.code.korti.simplenotepad.presentation.list

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.code.korti.simplenotepad.R
import com.code.korti.simplenotepad.data.db.models.Color
import com.code.korti.simplenotepad.data.db.models.Note
import com.code.korti.simplenotepad.databinding.FragmentNotesListBinding
import com.code.korti.simplenotepad.presentation.adapter.NoteAdapter
import com.code.korti.simplenotepad.presentation.adapter.NoteAdapterListener
import com.code.korti.simplenotepad.presentation.list.dialog.ColorListener
import com.code.korti.simplenotepad.presentation.list.dialog.DialogFragment
import com.code.korti.simplenotepad.utils.ItemOffsetDecoration
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NotesListFragment : Fragment(R.layout.fragment_notes_list), NoteAdapterListener,
    ColorListener {

    private val viewModel by viewModels<NotesViewModel>()

    private var noteAdapter: NoteAdapter? = null

    private val binding: FragmentNotesListBinding by viewBinding(FragmentNotesListBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        bind()
        initList()
        viewModel.loadList()
    }

    private fun bind() {
        viewModel.notesLiveData.observe(viewLifecycleOwner) { list ->
            showTextListEmpty(list.isEmpty())
            noteAdapter?.submitList(list)
            scrollListToTop()
        }
    }

    private fun initList() {
        noteAdapter = NoteAdapter(this, requireContext())
        binding.notesList.apply {
            adapter = noteAdapter
            addItemDecoration(ItemOffsetDecoration(requireContext()))
        }
        binding.notesList.adapter = noteAdapter

    }

    private fun showMenu(v: View, @MenuRes menuRes: Int, noteId: Long) {
        val popup = PopupMenu(v.context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.option_1 -> {
                    showDialog(noteId)
                    true
                }
                R.id.option_2 -> {
                    DialogFragment(requireContext(), noteId)
                        .show(childFragmentManager, "DialogTag")
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun showTextListEmpty(isListEmpty: Boolean) {
        binding.listEmptyTextView.isVisible = isListEmpty
    }

    private fun scrollListToTop() {
        lifecycleScope.launch {
            delay(10)
            binding.notesList.scrollToPosition(0)
        }
    }

    private fun showDialog(id: Long) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(resources.getString(R.string.remove_this_note))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.remove)) { dialog, which ->
                viewModel.removeNoteById(id) { showSnackbar(it) }
            }
            .show()
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.constraintLayout, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onNoteClicked(cardView: View, note: Note) {
        val noteCardDetailTransitionName = getString(R.string.note_card_detail_transition_name)
        val extras = FragmentNavigatorExtras(cardView to noteCardDetailTransitionName)
        val directions =
            NotesListFragmentDirections.actionNotesListFragmentToNoteDetailFragment(note.id)
        findNavController().navigate(directions, extras)
    }

    override fun onNoteMenuClicked(v: View, noteId: Long) {
        showMenu(v, R.menu.popup_menu, noteId)
    }

    override fun changeColorOfNote(color: Color, noteId: Long) {
        viewModel.updateNote(noteId, color)
    }

    override fun onDestroy() {
        super.onDestroy()
        noteAdapter = null
    }
}