package org.ahivs.customview.basic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import org.ahivs.customview.R

class MultiRect @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mainRect: Rect = Rect()
    private val smallRect: Rect = Rect()
    private val colors = listOf(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE)
    private val rotation = listOf(10f, 20f, 30f, 40f)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mainRect.set(paddingLeft, paddingTop, w - paddingRight, h - paddingBottom)
        smallRect.set(0, 0, mainRect.width() / 4, mainRect.height() / 4)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val w = mainRect.width() / 4
        val h = mainRect.height() / 4
        canvas?.apply {
            clipRect(mainRect)
            paint.color = Color.LTGRAY
            drawRect(mainRect, paint)
        }

        for (i in 0 until 4) {
            canvas?.apply {
                save()
                translate(
                    (mainRect.left + w * i).toFloat(),
                    (mainRect.top + h * i).toFloat()
                )
                rotate(rotation[i])
                paint.color = colors[i]
                drawRect(smallRect, paint)
                restore()
            }
        }
    }

}