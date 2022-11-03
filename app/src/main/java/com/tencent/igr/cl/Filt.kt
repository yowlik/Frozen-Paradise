package com.tencent.igr.cl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tencent.igr.R
import com.tencent.igr.bl.Gamm
import com.tencent.igr.cl.Appa.Companion.soupch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Filt : AppCompatActivity() {
    lateinit var jsoup: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filt)
        jsoup = ""
        val asyncJs = AsyncJs(applicationContext)

        val job = GlobalScope.launch(Dispatchers.IO) {
            jsoup = asyncJs.coTask()
        }

        runBlocking {
            job.join()
            Log.d("jsoup status", jsoup)
            if (jsoup == soupch) {
                Intent(applicationContext, Gamm::class.java).also { startActivity(it) }
            } else {
                Intent(applicationContext, Webster::class.java).also { startActivity(it) }
            }
            finish()
        }
    }
}