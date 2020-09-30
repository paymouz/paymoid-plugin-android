/*
 * Copyright (c) Siyavushkhon Kholmatov, 26/9/2020 at 23:58
 */

package uz.paymo.identifier.ui

import android.content.Context
import android.view.View
import kotlinx.android.synthetic.main.paymo_id_bottomsheet_install_app_promo.view.*
import uz.paymo.identifier.R
import uz.paymo.identifier.ui.base.RoundedBottomSheetDialog

class InstallAppPromoBottomSheet(
    context: Context,
    onCancel: () -> Unit,
    onInstall: () -> Unit
) : RoundedBottomSheetDialog(context) {
    private val view: View = layoutInflater.inflate(R.layout.paymo_id_bottomsheet_install_app_promo, null)

    init {
        setOnCancelListener { onCancel.invoke() }
        view.cancelButton?.setOnClickListener { cancel() }
        view.installButton?.setOnClickListener {
            dismiss()
            onInstall.invoke()
        }
        setContentView(view)
    }
}