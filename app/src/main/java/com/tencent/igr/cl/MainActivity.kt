package com.tencent.igr.cl

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.orhanobut.hawk.Hawk
import com.tencent.igr.bl.Gamm
import com.tencent.igr.cl.Appa.Companion.af
import com.tencent.igr.cl.Appa.Companion.c1
import com.tencent.igr.cl.Appa.Companion.sf
import com.tencent.igr.cl.Appa.Companion.D1
import com.tencent.igr.cl.Appa.Companion.chekpar1
import com.tencent.igr.cl.Appa.Companion.checkpar2
import com.tencent.igr.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var bindMain: ActivityMainBinding

    var checker: String = "null"
    lateinit var jsoup: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindMain.root)
        jsoup = ""

        deeper(this)

        val prefs = getSharedPreferences("ActivityPREF", MODE_PRIVATE)
        if (prefs.getBoolean("activity_exec", false)) {
//            toTestGrounds()
//            finish()

            when (Hawk.get<String>(sf)) {
                "2" -> {
                    skipa()
                }
                else -> {
                    testa()
                }
            }
        } else {
            val exec = prefs.edit()
            exec.putBoolean("activity_exec", true)
            exec.apply()

            val job = GlobalScope.launch(Dispatchers.IO) {
                checker = getCheckCode(chekpar1+checkpar2)
            }
            runBlocking {
                try {
                    job.join()
                } catch (_: Exception){
                }
            }

            when (checker) {
                "1" -> {
                    AppsFlyerLib.getInstance()
                        .init(af, conversionDataListener, applicationContext)
                    AppsFlyerLib.getInstance().start(this)
                    afNullRecordedOrNotChecker(1500)
                }
                "2" -> {
                    skipa()

                }
                "0" -> {
                    testa()
                }
            }
        }
    }



    private suspend fun getCheckCode(link: String): String {
        val url = URL(link)
        val oneStr = "1"
        val twoStr = "2"
        val activeStrn = "0"
        val urlConnection = withContext(Dispatchers.IO) {
            url.openConnection()
        } as HttpURLConnection

        return try {
            when (val text = urlConnection.inputStream.bufferedReader().readText()) {
                "2" -> {
                    Hawk.put(sf, twoStr)
                    Log.d("jsoup status", text)
                    twoStr
                }
                "1" -> {
                    Log.d("jsoup status", text)
                    oneStr
                }
                else -> {
                    Log.d("jsoup status", "is null")
                    activeStrn
                }
            }
        } finally {
            urlConnection.disconnect()
        }

    }

    private fun afNullRecordedOrNotChecker(timeInterval: Long): Job {

        return CoroutineScope(Dispatchers.IO).launch {
            while (NonCancellable.isActive) {
                val hawk1: String? = Hawk.get(c1)
                if (hawk1 != null) {
                    Log.d("TestInUIHawk", hawk1.toString())
                    testa()
                    break
                } else {
                    val hawk1: String? = Hawk.get(c1)
                    Log.d("TestInUIHawkNulled", hawk1.toString())
                    delay(timeInterval)
                }
            }
        }
    }



    val conversionDataListener = object : AppsFlyerConversionListener {
        override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {

            val dataGotten = data?.get("campaign").toString()
            Hawk.put(c1, dataGotten)
        }

        override fun onConversionDataFail(p0: String?) {

        }

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {

        }

        override fun onAttributionFailure(p0: String?) {
        }
    }

    private fun testa() {
        Intent(this, Filt::class.java)
            .also { startActivity(it) }
        finish()
    }

    private fun skipa() {
        Intent(this, Gamm::class.java)
            .also { startActivity(it) }
        finish()
    }
    fun deeper(context: Context) {

        AppLinkData.fetchDeferredAppLinkData(
            context
        ) { appLinkData: AppLinkData? ->
            appLinkData?.let {
                val params = appLinkData.targetUri.host
                Hawk.put(D1,params.toString())
            }
        }
    }
}