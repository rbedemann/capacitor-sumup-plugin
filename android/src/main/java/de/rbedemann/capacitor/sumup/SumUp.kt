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
    CHECKOUT(2)
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

    @PluginMethod
    fun checkout(call: PluginCall) {
        Log.i(TAG, "checkout started")
        saveCall(call)

        val payment = SumUpPayment
                .builder()
                .total(BigDecimal(call.data.getDouble("total")))
                .currency(SumUpPayment.Currency.valueOf(call.data.getString("currency")))

        if (call.data.has("title")) {
            payment.title(call.data.getString("title"))
        }

        if (call.data.has("receiptEmail")) {
            payment.receiptEmail(call.data.getString("receiptEmail"))
        }

        if (call.data.has("receiptSMS")) {
            payment.receiptSMS(call.data.getString("receiptSMS"))
        }

        if (call.data.has("additionalInfo")) {
            val additionalInfoLines = call.data.getJSObject("additionalInfo")

            additionalInfoLines
                    .keys().forEach {
                        payment.addAdditionalInfo(it, additionalInfoLines.getString(it))
                    }

        }

        if (call.data.has("foreignTransactionId")) {
            payment.foreignTransactionId(call.data.getString("foreignTransactionId"))
        }

        if (call.data.optBoolean("skipSuccessScreen")) {
            payment.skipSuccessScreen()
        }

        SumUpAPI.checkout(null, payment.build(), RequestCodes.CHECKOUT.code)
    }

    override fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.handleOnActivityResult(requestCode, resultCode, data)

        if (savedLastCall == null) return

        val sumUpResultCode = data.extras?.getInt(SumUpAPI.Response.RESULT_CODE)
        val sumUpResultMessage = data.extras?.getString(SumUpAPI.Response.MESSAGE)

        if (requestCode == RequestCodes.LOGIN.code || requestCode == RequestCodes.CHECKOUT.code) {
            val result = JSObject()
            result.put("code", sumUpResultCode)
            result.put("message", sumUpResultMessage)

            if (resultCode > 0) {
                savedCall.resolve(result)
            } else {
                savedCall.reject(sumUpResultMessage, sumUpResultCode.toString())
            }
        }
    }
}
