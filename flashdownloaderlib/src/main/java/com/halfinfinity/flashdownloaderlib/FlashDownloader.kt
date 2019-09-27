package com.halfinfinity.flashdownloaderlib

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.halfinfinity.flashdownloaderlib.disposable.DisposeOnStopObserver
import com.halfinfinity.flashdownloaderlib.requestApi.ImageRequestFinalStep
import com.halfinfinity.flashdownloaderlib.requestApi.JsonRequestFinalStep
import com.halfinfinity.flashdownloaderlib.requestApi.JsonResourceRequest
import com.halfinfinity.flashdownloaderlib.requestApi.ResourceRequest

class FlashDownloader {

    companion object{

        val disposeObserverMap = HashMap<String, DisposeOnStopObserver>()

        fun loadImageFrom(activity: AppCompatActivity, url: String) : ImageRequestFinalStep {
            addObserverInMapIfNeeded(activity)
            return loadImageFrom(activity.toString(), url)
        }

        fun loadImageFrom(fragment: Fragment, url: String) : ImageRequestFinalStep {
            addObserverInMapIfNeeded(fragment)
            return loadImageFrom(fragment.toString(), url)
        }

        fun loadJsonFrom(activity: AppCompatActivity, url: String) : JsonRequestFinalStep {
            addObserverInMapIfNeeded(activity)
            return loadJsonFrom(activity.toString(), url)
        }

        fun loadJsonFrom(fragment: Fragment, url: String) : JsonRequestFinalStep {
            addObserverInMapIfNeeded(fragment)
            return loadJsonFrom(fragment.toString(), url)
        }

        private fun loadImageFrom(disposeObserverIdentifier: String, url: String) : ImageRequestFinalStep {
            return ImageRequestFinalStep(ResourceRequest(disposeObserverIdentifier, url))
        }

        private fun loadJsonFrom(disposeObserverIdentifier: String, url: String) : JsonRequestFinalStep {
            return JsonRequestFinalStep(JsonResourceRequest(disposeObserverIdentifier, url))
        }

        private fun addObserverInMapIfNeeded(activity: AppCompatActivity){
            val activityIdentifier = activity.toString()
            if(disposeObserverMap[activityIdentifier] == null) {
                val disposeObserver = DisposeOnStopObserver(activityIdentifier)
                activity.lifecycle.addObserver(disposeObserver)
                disposeObserverMap[activityIdentifier] = disposeObserver
            }
        }

        private fun addObserverInMapIfNeeded(fragment: Fragment){
            val fragmentIdentifier = fragment.toString()
            if(disposeObserverMap[fragmentIdentifier] == null) {
                val disposeObserver = DisposeOnStopObserver(fragmentIdentifier)
                fragment.lifecycle.addObserver(disposeObserver)
                disposeObserverMap[fragmentIdentifier] = disposeObserver
            }
        }
    }
}