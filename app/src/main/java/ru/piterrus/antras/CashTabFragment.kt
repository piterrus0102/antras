package ru.piterrus.antras

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.fonts.Font
import android.graphics.fonts.FontFamily
import android.graphics.fonts.FontStyle
import android.os.Bundle
import android.text.Spannable
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment


class CashTabFragment: Fragment() {

    lateinit var cashTextView: TextView
    lateinit var textView16: TextView
    lateinit var textView17: TextView
    lateinit var textView19: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_cash_tab, null)
        cashTextView = v.findViewById(R.id.cashTextView)
        textView16 = v.findViewById(R.id.textView16)
        textView17 = v.findViewById(R.id.textView17)
        textView19 = v.findViewById(R.id.textView19)
        var first = "Цена резюме для скачивания составляет\n"
        var another = "119"
        var next = "рублей"
        cashTextView.setText(first + another + " " + next, TextView.BufferType.SPANNABLE)
        var s = cashTextView.text as Spannable
        var start = first.length
        var end = start + another.length;
        s.setSpan(ForegroundColorSpan(Color.parseColor("#0B835B")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        s.setSpan(AbsoluteSizeSpan(24, true), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        first = "В течении"
        another = "30"
        next = "дней Вы сможете использовать сервис без ограничений!"
        textView16.setText(first + " " + another + " " + next, TextView.BufferType.SPANNABLE)
        s = textView16.text as Spannable
        start = first.length
        end = start + another.length + 1
        val boldSpan = StyleSpan(Typeface.BOLD)
        s.setSpan(boldSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        first = "Оплата производится через сервис\n"
        another = "Я"
        next = "ндекс.Касса"
        textView17.setText(first + another + next, TextView.BufferType.SPANNABLE)
        s = textView17.text as Spannable
        start = first.length
        end = start + another.length
        val end2 = end + next.length
        val boldSpan2 = StyleSpan(Typeface.BOLD)
        s.setSpan(ForegroundColorSpan(Color.parseColor("#FF0000")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        s.setSpan(boldSpan2, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        s.setSpan(boldSpan, end, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        first = "Принимая данное соглашение, Вы подтверждаете, что уведомлены о том, что конструктор резюме"
        another = "ANTRAS"
        next = "является платным приложением."
        textView19.setText(first + " " + another + " " + next, TextView.BufferType.SPANNABLE)
        s = textView19.text as Spannable
        start = first.length
        end = start + another.length + 1
        s.setSpan(boldSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return v
    }
}