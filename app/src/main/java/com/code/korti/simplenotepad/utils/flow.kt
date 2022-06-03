package com.code.korti.simplenotepad.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun EditText.textChangedFlow(): Flow<String> {
    return callbackFlow<String> {
        val textChangedListener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sendBlocking(p0?.toString().orEmpty())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        this@textChangedFlow.addTextChangedListener(textChangedListener)
        awaitClose{
            this@textChangedFlow.removeTextChangedListener(textChangedListener)
        }
    }
}
