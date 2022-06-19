package com.example.kyc_kotlin_sample

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


class WebBridge {

//    private val TAG:String = "AdroidBridge"
    private var mAppView: WebView? = null
    private var mContext: MainActivity? = null
    private val handler = android.os.Handler()

    fun WebBridge(_mAppView: WebView?, _mContext: MainActivity?) {
        mAppView = _mAppView
        mContext = _mContext
    }

    @JavascriptInterface
    fun receive(data: String) {

//        Log.d("result data:", data)

        val close = "{\"result\": \"close\"}"
        val success = "{\"result\": \"success\"}"
        val complete = "{\"result\": \"complete\"}"

        val decodedData = decodedReceiveData(data)
        if(decodedData.toString() == close)
            Log.d("close", "KYC가 완료되지 않았습니다.")
        else if(decodedData.toString() == success)
            Log.d("success", "KYC 작업이 성공했습니다.")
        else if(decodedData.toString() == complete)
            Log.d("complete", "KYC가 완료되었습니다.")
    }

    private fun decodedReceiveData(data:String){

        var decoded : String = data.fromBase64()
        var decodedText = URLDecoder.decode(decoded, "UTF-8")
    }

    private fun String.fromBase64(): String{

        return String(
            android.util.Base64.decode(this.toByteArray(), android.util.Base64.DEFAULT),
            StandardCharsets.UTF_8
        )
    }
}