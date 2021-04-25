package me.androiddev.ui_bonus.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import me.androiddev.ui_bonus.R
import java.text.SimpleDateFormat


@BindingAdapter("format_date")
fun formatDate(textView: TextView, arg1: String?) {
    if (arg1.isNullOrBlank())
        return

    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val minifyFormatter = SimpleDateFormat("MM.dd")

    formatter.parse(arg1)?.let { date ->

        val formattedDate = String.format(
            textView.context.getString(R.string.format_burn_date),
            minifyFormatter.format(date)
        )
        textView.text = formattedDate
    }

}