package de.rbedemann.capacitor.sumup

import android.content.Intent
import android.util.Log
import com.getcapacitor.*
import com.sumup.merchant.reader.api.SumUpAPI
import com.sumup.merchant.reader.api.SumUpLogin
import com.sumup.merchant.reader.api.SumUpPayment
import com.sumup.merchant.reader.api.SumUpState
import java.math.BigDecimal


enum class RequestCodes(val code: Int) {
    LOGIN(1),
}

const val TAG = "Capacitor:SumUpPlugin"

@NativePlugin(requestCodes = [1, 2, 3])
class SumUp : Plugin() {

    override fun load() {
        Log.d(TAG, "Initializing Plugin")
        SumUpState.init(context)
    }


    @PluginMethod
    fun login(call: PluginCall) {
        Log.d(TAG, "Logging in")
        saveCall(call)

        val affiliateKey = call.data.getString("affiliateKey")

        val sumupLogin = SumUpLogin.builder(affiliateKey)

        if (call.data.has(affiliateKey)) {
            val accessToken = call.data.getString("accessToken")
            sumupLogin.accessToken(accessToken)
        }

        SumUpAPI.openLoginActivity(activity, sumupLogin.build(), RequestCodes.LOGIN.code)
    }

    override fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.handleOnActivityResult(requestCode, resultCode, data)

        if (savedLastCall == null) return

        val sumUpResultCode = data.extras?.getInt(SumUpAPI.Response.RESULT_CODE)
        val sumUpResultMessage = data.extras?.getString(SumUpAPI.Response.MESSAGE)

        if (requestCode == RequestCodes.LOGIN.code) {
            val result = JSObject()
            result.put("code", sumUpResultCode)
            result.put("message", sumUpResultMessage)

            if (resultCode > 0) {
                savedCall.resolve(result)
            } else {
                savedCall.reject(sumUpResultMessage, sumUpResultCode.toString() )
            }
        }
    }
}
