package com.code.korti.simplenotepad.presentation.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.code.korti.simplenotepad.R
import com.code.korti.simplenotepad.databinding.ActivityMainBinding
import com.code.korti.simplenotepad.presentation.list.NotesListFragmentDirections

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private var currentEmailId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBottomNavigationAndFab()
    }

    private fun setUpBottomNavigationAndFab() {
        binding.run {
            findNavController(R.id.fragmentContainerView).addOnDestinationChangedListener(
                this@MainActivity
            )
        }

        binding.fab.setOnClickListener {
            val directions = NotesListFragmentDirections.actionNotesListFragmentToAddingNoteFragment()
            findNavController(R.id.fragmentContainerView).navigate(directions)
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {

            R.id.notesListFragment -> {
                currentEmailId = -1
                setBottomAppBarForHome()
            }
            R.id.addingNoteFragment -> {
                currentEmailId = -1
                setBottomAppBarForAddNote()
            }
            R.id.noteDetailFragment -> {
                currentEmailId = -1
                setBottomAppBarForDetailNote()
            }
        }
    }

    private fun setBottomAppBarForHome() {
        binding.run {
            fab.setImageState(intArrayOf(-android.R.attr.state_activated), true)
            fab.visibility = View.VISIBLE
            fab.show()
        }
    }

    private fun setBottomAppBarForAddNote() {
        binding.run {
            fab.visibility = View.GONE
        }
    }

    private fun setBottomAppBarForDetailNote() {
        binding.run {
            fab.visibility = View.GONE
        }
    }
}