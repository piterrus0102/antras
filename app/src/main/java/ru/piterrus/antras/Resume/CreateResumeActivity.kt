package ru.piterrus.antras.Resume

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.piterrus.antras.R

class CreateResumeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_resume)
        supportActionBar?.hide()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragmentContainer, ResumeFirstStepFragment())
        ft.addToBackStack(null)
        ft.commit()
    }
}
