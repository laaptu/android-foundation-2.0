package org.ahivs.customview.basic

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class Triangle @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.FILL
    }
    private val path: Path = Path()

    private val mainRect: Rect = Rect()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mainRect.set(paddingLeft, paddingTop, w - paddingRight, h - paddingBottom)
        path.apply {
            reset()
            moveTo(mainRect.exactCenterX(), mainRect.top.toFloat())
            lineTo(mainRect.right.toFloat(), mainRect.bottom.toFloat())
            lineTo(mainRect.left.toFloat(), mainRect.bottom.toFloat())
            close()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawPath(path, paint)
        }
    }

}