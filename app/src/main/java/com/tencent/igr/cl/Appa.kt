package com.tencent.igr.cl

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Appa : Application() {
    companion object {
        const val af = "Pgoz2JxxmeC7RdTrj4fBbG"
        const val soupch = "admin"
        const val onesig = "f1fe3d1b-88c8-4788-abdf-9cff3538a598"

        val filterpar2 = "http://frozen"
        val filtpar1 = "paradise.xyz/go.php?to=1&"
        val chekpar1 = "http://frozenp"
        val checkpar2 = "aradise.xyz/apps.txt"

        val odone = "sub_id_1="

        var MAIN_ID: String? = ""
        var c1: String? = "c11"
        var D1: String? = "d11"
        var sf: String? = "check"

    }

    override fun onCreate() {
        super.onCreate()

        GlobalScope.launch(Dispatchers.IO) {
            applyDeviceId(context = applicationContext)
        }
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(onesig)
        Hawk.init(this).build()
    }

    private suspend fun applyDeviceId(context: Context) {
        val advertisingInfo = Adv(context)
        val idInfo = advertisingInfo.getAdvertisingId()
        Hawk.put(MAIN_ID, idInfo)
    }

}

class Adv (context: Context) {
    private val adInfo = AdvertisingIdClient(context.applicationContext)

    suspend fun getAdvertisingId(): String =
        withContext(Dispatchers.IO) {
            adInfo.start()
            val adIdInfo = adInfo.info
            Log.d("getAdvertisingId = ", adIdInfo.id.toString())
            adIdInfo.id
        }
}