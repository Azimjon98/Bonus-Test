package me.androiddev.logintestapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import me.androiddev.core.R


object UiHelper {

    const val layoutId = 146424

    @SuppressLint("ResourceType")
    fun disableMyWindow(activity: Activity) {

        if (activity.findViewById<View>(layoutId) != null) {
            activity.findViewById<View>(layoutId).setVisibility(View.VISIBLE)
            return
        }
        val bckView = View(activity)
        bckView.visibility = View.VISIBLE
        bckView.setBackgroundResource(R.color.progressBckColor)
        val param: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        param.addRule(RelativeLayout.CENTER_IN_PARENT)
        val layout = RelativeLayout(activity)
        layout.setId(layoutId)
        layout.addView(bckView, param)
        val baseParam: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        activity.window.addContentView(layout, baseParam)
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun enableMyWindow(activity: Activity) = try {
        val v: View = activity.findViewById(layoutId)
        v.visibility = View.GONE
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    } catch (e: Exception) {
    }
}