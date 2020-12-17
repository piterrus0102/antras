package ru.piterrus.antras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import ru.piterrus.antras.Resume.CreateResumeActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var createResumeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        createResumeButton = findViewById(R.id.createResumeButton)
        createResumeButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.createResumeButton -> {
                startActivity(Intent(this, CreateResumeActivity::class.java))
            }
        }
    }
}
