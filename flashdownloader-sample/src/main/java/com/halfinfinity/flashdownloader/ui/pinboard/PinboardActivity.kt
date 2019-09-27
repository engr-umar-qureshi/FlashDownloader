package com.halfinfinity.flashdownloader.ui.pinboard

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.halfinfinity.flashdownloader.R
import com.halfinfinity.flashdownloader.baseArchitecture.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class PinboardActivity : BaseActivity<PinboardViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initializeViewModel(PinboardViewModel::class.java)

        initPinboard()

    }

    private fun initPinboard() {
        rvPinboard.layoutManager = GridLayoutManager(this, 2)
        populatePinboardItems()
    }

    private fun populatePinboardItems() {
        showDialogNetworkProgress()
        mViewModel.getPinboardItems().observe(this, Observer { listPinboardItems ->
            rvPinboard.adapter =
                PinboardAdapter(this@PinboardActivity, listPinboardItems)
            hideDialogNetworkProgress()
        })
    }

}
