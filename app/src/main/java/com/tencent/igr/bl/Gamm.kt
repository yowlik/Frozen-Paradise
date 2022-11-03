package com.tencent.igr.bl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.tencent.igr.R
import com.tencent.igr.databinding.ActivityGammBinding

class Gamm : AppCompatActivity() {
    lateinit var bind:ActivityGammBinding
    lateinit var i:String
    val rand= mutableListOf<Int>(R.drawable.imag,R.drawable.imaga,R.drawable.imagas)
    val check1= mutableListOf<String>("1","1","1","1","1","1","1","1","1","1","1","1",
        "1","1","1","2","3","4")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityGammBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.card.setOnClickListener {
            if(bind.card.text=="1"){
                i=check1.random()
                cheksl(i,bind.card)

            }
            else{
                bind.card.text="1"
                bind.card.setBackgroundResource(R.drawable.card)
                bind.textas.text="Click More"
            }

        }
    }

    fun cheksl(i:String,im: Button){
        if(i=="1"){
            im.setText("1")
            im.setBackgroundResource(R.drawable.card)
            bind.textas.text="Click More"
        }
        else {
            im.setText("2")
            im.setBackgroundResource(rand.random())
            bind.textas.text="You find treasure\n Click on him to restart"
        }
    }
}