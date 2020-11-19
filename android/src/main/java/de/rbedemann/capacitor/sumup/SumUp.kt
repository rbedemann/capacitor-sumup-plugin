package de.rbedemann.capacitor.sumup

import com.getcapacitor.*

@NativePlugin
class SumUp : Plugin() {
    @PluginMethod
    fun echo(call: PluginCall) {
        val value = call.getString("value")
        val ret = JSObject()
        ret.put("value", value)
        call.success(ret)
    }
}