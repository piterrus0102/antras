package ru.piterrus.antras

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class StepThreeFragment: Fragment() {

    lateinit var textView30: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_step_three, null)

        textView30 = v.findViewById(R.id.textView30)

        val first = "  Оплатите с помощью банковской карты Ваше резюме, его стоимость составляет "
        val another = "119"
        val next = "рублей"
        textView30.setText(first + another + " " + next, TextView.BufferType.SPANNABLE)
        val s = textView30.text as Spannable
        val start = first.length
        val end = start + another.length;
        s.setSpan(ForegroundColorSpan(Color.parseColor("#0B835B")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return v
    }
}