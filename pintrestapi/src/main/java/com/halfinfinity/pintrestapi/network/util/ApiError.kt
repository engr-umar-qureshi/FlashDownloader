package com.halfinfinity.pintrestapi.network.util

import android.content.Context
import com.halfinfinity.pintrestapi.R
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

public class ApiError {
    private var mResponseErrorMessage: String? = null
    private var mResponseError: Code? = null

    fun setResponseErrorMessage(responseErrorMessage: String) {
        this.mResponseErrorMessage = responseErrorMessage
    }

    fun setResponseError(responseError: Code) {
        this.mResponseError = responseError
    }

    fun translate(context: Context): String {
        when (mResponseError) {
            Code.CONNECTION_FAILED -> return context.resources.getString(R.string.error_msg_connection)
            Code.CALL_FAILED -> return context.resources.getString(R.string.error_msg_call)
            Code.FAILED_RESPONSE -> return mResponseErrorMessage ?: context.resources.getString(
                R.string.error_msg_response
            )
            else -> return context.resources.getString(R.string.error_msg_invalid)
        }
    }

    enum class Code {
        CONNECTION_FAILED,
        CALL_FAILED,
        FAILED_RESPONSE,
        UNKNOWN

    }

   companion object{
       fun translateFailureToApiError(throwable: Throwable): ApiError {
           val apiError = ApiError()
           when(throwable)
           {
               is HttpException -> {
               val responseBody = throwable.response().errorBody()
               apiError.setResponseError(Code.FAILED_RESPONSE)
               try {
                   apiError.setResponseErrorMessage(responseBody!!.string())
               } catch (e: Exception) {
                   apiError.setResponseError(Code.CALL_FAILED)
               }
               }

               is SocketTimeoutException -> apiError.setResponseError(Code.CONNECTION_FAILED)

               is IOException -> apiError.setResponseError(Code.CALL_FAILED)

               else -> apiError.setResponseError(ApiError.Code.UNKNOWN)
           }

           return apiError
       }
   }
}