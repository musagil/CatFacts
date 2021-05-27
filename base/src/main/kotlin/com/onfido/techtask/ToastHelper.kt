package com.onfido.techtask

import android.widget.Toast
import androidx.fragment.app.Fragment
import javax.inject.Inject

@OpenForTesting
class ToastHelper @Inject constructor(
    private val fragment: Fragment
) {

    fun show(text: String, duration: Int = Toast.LENGTH_LONG) =
        Toast.makeText(fragment.context, text, duration).show()
}
