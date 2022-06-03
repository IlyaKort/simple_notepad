package com.code.korti.simplenotepad.presentation.list.dialog

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.code.korti.simplenotepad.R
import com.code.korti.simplenotepad.data.db.models.Color
import com.code.korti.simplenotepad.databinding.ListColorsDialogBinding
import timber.log.Timber

class DialogFragment(
    context: Context,
    private val noteId: Long
) : DialogFragment(R.layout.list_colors_dialog) {

    private var colorAdapter: ColorAdapter? = null
    private val binding: ListColorsDialogBinding by viewBinding(ListColorsDialogBinding::bind)

    private val whiteColor = ColorDrawable(ContextCompat.getColor(context, R.color.white))
    private val greenColor = ColorDrawable(ContextCompat.getColor(context, R.color.green))
    private val blueColor = ColorDrawable(ContextCompat.getColor(context, R.color.blue))
    private val redColor = ColorDrawable(ContextCompat.getColor(context, R.color.red))

    private val map = mapOf(
        Pair(context.resources.getString(R.string.white), whiteColor),
        Pair(context.resources.getString(R.string.green), greenColor),
        Pair(context.resources.getString(R.string.blue), blueColor),
        Pair(context.resources.getString(R.string.red), redColor)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        val parentActivity: ColorListener? = parentFragment.let { it as? ColorListener }

        colorAdapter = ColorAdapter(map) { color ->
            if (colorDetection(color) == null) {
                Timber.e("сolor not processed")
            } else {
                Log.d("NotesListFragment", "color = ${colorDetection(color)}")
                parentActivity?.changeColorOfNote(colorDetection(color)!!, noteId)
                dismiss()
            }
        }
        with(binding.colorsList) {
            adapter = colorAdapter
            setHasFixedSize(true)
        }
    }

    private fun colorDetection(color: ColorDrawable): Color? {
        return when (color) {
            whiteColor -> Color.WHITE
            greenColor -> Color.GREEN
            blueColor -> Color.BLUE
            redColor -> Color.RED
            else -> null
        }
    }
}