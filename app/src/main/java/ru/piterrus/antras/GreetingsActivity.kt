package ru.piterrus.antras

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.*

class GreetingsActivity : AppCompatActivity() {

    var k = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_greetings)
        supportActionBar?.hide()
        val mainPref = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        k = mainPref.getInt("entercount",0)
        Timer().schedule(LoadTimerTask(), 1500)
    }

    inner class LoadTimerTask: TimerTask(){
        override fun run() {
            if(k == 0){
                val intent = Intent(this@GreetingsActivity, FirstEnterActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(this@GreetingsActivity, MainActivity::class.java)
                startActivity(intent)
            }
            //val intent = Intent(this@GreetingsActivity, FirstEnterActivity::class.java)
            //startActivity(intent)
            k++
            val mainPref = getSharedPreferences("Settings", Context.MODE_PRIVATE)
            val mainEditor = mainPref.edit()
            mainEditor.putInt("entercount", k)
            mainEditor.apply()

        }

    }
}
