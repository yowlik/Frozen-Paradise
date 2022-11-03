package com.tencent.igr.cl

import android.content.Context
import android.util.Log
import com.orhanobut.hawk.Hawk
import com.tencent.igr.cl.Appa.Companion.c1
import com.tencent.igr.cl.Appa.Companion.D1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class AsyncJs (val context: Context) {
    private var jsoup: String = ""

    suspend fun coTask(): String {

        val nameParameter: String? = Hawk.get(c1)
        val appLinkParameter: String? = Hawk.get(D1)


        val tsask = "${Appa.filterpar2}${Appa.filtpar1}${Appa.odone}$nameParameter"
        val linsra = "${Appa.filterpar2}${Appa.filtpar1}${Appa.odone}$appLinkParameter"

        withContext(Dispatchers.IO) {
            //changed logical null to string null
            if (nameParameter != "null") {
                getDocSecretKey(tsask)
                Log.d("Check1C", tsask)
            } else {
                getDocSecretKey(linsra)
                Log.d("Check1C", linsra)
            }
        }
        return jsoup
    }

    private fun getDocSecretKey(link: String) {
        val text = Jsoup.connect(link).get().text()
        Log.d("Text from jsoup", text)
        jsoup = text
    }
}