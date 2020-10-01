package uz.paymo.identifier.ui

import android.content.Context
import android.view.View
import kotlinx.android.synthetic.main.paymo_id_bottomsheet_loading.view.*
import uz.paymo.identifier.R
import uz.paymo.identifier.ui.base.RoundedBottomSheetDialog

internal class LoadingBottomSheet(
    context: Context
) : RoundedBottomSheetDialog(context) {
    private val view: View = layoutInflater.inflate(R.layout.paymo_id_bottomsheet_loading, null)
    private var errorOccurred: Boolean = false
    var onCancel: (() -> Unit)? = null

    init {
        setOnCancelListener { if (!errorOccurred) onCancel?.invoke() }
        setContentView(view)
    }

    fun setError(message: String?) {
        errorOccurred = true
        view.progressBar?.visibility = View.GONE
        view.errorInfoText?.visibility = View.VISIBLE
        view.errorInfoText?.text = message ?: context.getString(R.string.paymo_id_unknown_error)
    }
}