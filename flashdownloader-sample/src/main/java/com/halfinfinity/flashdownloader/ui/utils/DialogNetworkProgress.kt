package com.halfinfinity.flashdownloader.ui.utils

import android.app.ProgressDialog
import android.content.Context
import com.halfinfinity.flashdownloader.R

class DialogNetworkProgress {

    companion object {
        fun getDialog(mContext: Context): ProgressDialog {
            return ProgressDialog(mContext).apply {
                setMessage(mContext.resources.getString(R.string.txt_network_loading))
                setProgressStyle(ProgressDialog.STYLE_SPINNER)
                setCanceledOnTouchOutside(false)
                isIndeterminate = true
                progress = 0
            }
        }
    }
}
