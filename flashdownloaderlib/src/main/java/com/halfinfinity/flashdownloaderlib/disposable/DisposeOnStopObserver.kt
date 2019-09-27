package com.halfinfinity.flashdownloaderlib.disposable

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.halfinfinity.flashdownloaderlib.FlashDownloader


class DisposeOnStopObserver(private val identifier: String, private val listResourceRequestDisposable: ArrayList<ResourceRequestDisposable> = ArrayList()) : LifecycleObserver {

    public fun addResourceRequestDisposable(requestDisposable: ResourceRequestDisposable){
        listResourceRequestDisposable.add(requestDisposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        for(disposable in listResourceRequestDisposable){
            disposable.cancelRequest()
        }
        FlashDownloader.disposeObserverMap.remove(identifier)
    }
}