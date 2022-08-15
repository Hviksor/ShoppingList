package com.example.shoppinglist.prsentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorInput")
fun bindErrorInput(textInputLayout: TextInputLayout, errorInput: Boolean) {
    if (errorInput) {
        textInputLayout.error = "Error"
    } else {
        textInputLayout.error = null
    }
}

@BindingAdapter("numberToString")
fun bindNumberToString(textView: TextView, number: Int) {
    textView.text = number.toString()

}