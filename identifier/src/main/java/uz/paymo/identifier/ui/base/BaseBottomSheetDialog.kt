/*
 * Copyright (c) Siyavushkhon Kholmatov, 25/8/2020 at 16:07
 */
package uz.paymo.identifier.ui.base

import android.content.Context
import android.content.DialogInterface
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog

abstract class BaseBottomSheetDialog : BottomSheetDialog {

    private val bottomSheetCallback: BottomSheetCallback = object : BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, @BottomSheetBehavior.State newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                cancel()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
        }
    }

    constructor(context: Context) : super(context) {}
    constructor(context: Context, theme: Int) : super(context, theme) {}
    protected constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)

    fun removeDefaultCallback() {
        behavior.removeBottomSheetCallback(bottomSheetCallback)
    }
}