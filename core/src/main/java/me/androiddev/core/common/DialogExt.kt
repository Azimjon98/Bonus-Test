package me.androiddev.core.common


import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import me.androiddev.core.R

fun Context.showAlertDialog(
    message: CharSequence,
    title: CharSequence? = null,
    buttonAction: DialogInterface.OnClickListener? = null,
    dismissListener: DialogInterface.OnDismissListener? = null,
    init: (MaterialAlertDialogBuilder.() -> Unit)? = null
): AlertDialog =
    MaterialAlertDialogBuilder(this).apply {
        this.setMessage(message)
        if (title != null)
            this.setTitle(title)

        setPositiveButton(resources.getString(R.string.accept)) { dialog, w ->
            dialog.dismiss()
            buttonAction?.onClick(dialog, w)
        }
        dismissListener?.let { setOnDismissListener(it) }

        if (init != null) init()
    }.show()
