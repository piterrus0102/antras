package ru.piterrus.antras

import android.content.Context
import android.graphics.Canvas
import android.widget.TextView
import android.util.AttributeSet

class MirroredTextView(context: Context?, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    override fun onDraw(canvas: Canvas?) {
        canvas?.translate(width.toFloat(), 0F)
        canvas?.scale(-1F, 1F)
        super.onDraw(canvas)
    }
}