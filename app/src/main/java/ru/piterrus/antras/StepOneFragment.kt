package ru.piterrus.antras

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.jetbrains.anko.find

class StepOneFragment: Fragment() {

    lateinit var textView24: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_step_one, null)

        textView24 = v.findViewById(R.id.textView24)

        val first = "Примечание. "
        val another = "В нашем конструкторе нет обязательных полей или пунктов. Более подробно о правилах заполнения Вы можете ознакомиться в разделе "
        val next = "статьи."
        textView24.setText(first + another + next, TextView.BufferType.SPANNABLE)
        val s = textView24.text as Spannable
        val start = first.length
        val end = start + another.length;
        val end2 = end + next.length
        val boldSpan = StyleSpan(Typeface.BOLD)
        val boldSpan2 = StyleSpan(Typeface.BOLD)
        s.setSpan(boldSpan2, 0, start, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        s.setSpan(ForegroundColorSpan(Color.parseColor("#2C98F0")), end, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        s.setSpan(boldSpan, end, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return v
    }

}