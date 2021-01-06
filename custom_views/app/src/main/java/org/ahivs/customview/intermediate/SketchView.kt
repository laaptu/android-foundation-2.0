package org.ahivs.customview.intermediate

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import org.ahivs.customview.R
import org.ahivs.customview.utils.getColor
import kotlin.math.abs

class SketchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    View(context, attrs, defStyle) {

    private var bgColor = 0
    private var drawColor = 0

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val frameRect = Rect()

    private var prevX = 0f
    private var prevY = 0f
    private val TOUCH_TOLERANCE = 4f

    private lateinit var extraBitmap: Bitmap
    private lateinit var extraCanvas: Canvas

    init {
        bgColor = getColor(R.color.opaque_orange)
        drawColor = getColor(R.color.opaque_yellow)

        paint.apply {
            color = drawColor
            isDither = true
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 12f
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(bgColor)

        val offSet = 40
        frameRect.set(offSet, offSet, w - offSet, h - offSet)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawBitmap(extraBitmap, 0f, 0f, null)
            drawRect(frameRect, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            when (action) {
                MotionEvent.ACTION_DOWN -> touchStart(x, y)
                MotionEvent.ACTION_MOVE -> {
                    touchMove(x, y)
                    invalidate()
                }
                MotionEvent.ACTION_UP -> touchUp()
            }
        }
        return true
    }

    private fun touchStart(x: Float, y: Float) {
        path.moveTo(x, y)
        prevX = x
        prevY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = abs(x - prevX)
        val dy = abs(y - prevY)

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(prevX, prevY, (x + prevX) / 2, (y + prevY) / 2)
            prevX = x
            prevY = y
            extraCanvas.drawPath(path, paint)
        }
    }

    private fun touchUp() {
        path.reset()
    }
}