package com.halfinfinity.flashdownloader.baseArchitecture

import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.halfinfinity.flashdownloader.ui.utils.DialogNetworkProgress
import com.halfinfinity.flashdownloader.R

open class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    private var dialogNetworkProgress: ProgressDialog? = null

    private var alertDialog: AlertDialog? = null

    protected lateinit var mViewModel: T

    fun showDialogNetworkProgress() {
        if (dialogNetworkProgress == null) {
            dialogNetworkProgress = DialogNetworkProgress.getDialog(this)
        }
        dialogNetworkProgress!!.show()
    }

    fun hideDialogNetworkProgress() {
        if (dialogNetworkProgress != null && dialogNetworkProgress!!.isShowing) {
            dialogNetworkProgress!!.dismiss()
        }
    }

    fun showAlertDialog(alertMessage: String) {
        if (alertDialog == null) {
            alertDialog =
                AlertDialog.Builder(this).setPositiveButton(getResources().getString(R.string.txt_ok), null).create()

        }
        alertDialog!!.setMessage(alertMessage)
        alertDialog!!.show()
    }

    override fun onDestroy() {
        if (dialogNetworkProgress != null && dialogNetworkProgress!!.isShowing) {
            dialogNetworkProgress!!.dismiss()
        }

        if (alertDialog != null && alertDialog!!.isShowing) {
            alertDialog!!.dismiss()
        }
        super.onDestroy()

    }

    private fun applyErrorHandling() {
        mViewModel.getLiveError().observe(this, Observer { apiError ->
            hideDialogNetworkProgress()
            showAlertDialog(apiError.translate(this))
        })
    }

    protected fun initializeViewModel(viewModelClass: Class<T>) {
        mViewModel = ViewModelProviders.of(this).get(viewModelClass)
        applyErrorHandling()
    }
}
