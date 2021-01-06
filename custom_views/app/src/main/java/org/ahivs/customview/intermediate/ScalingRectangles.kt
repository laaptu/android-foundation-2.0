package org.ahivs.customview.intermediate

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import java.util.*
import kotlin.math.abs

class ScalingRectangles @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    View(context, attrs, defStyle) {

    private val random = Random()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect = Rect()

    private val INCREMENT = 40
    private var offSet = INCREMENT
    private var centerX = 0f
    private var centerY = 0f
    private var prevX = 0f
    private var prevY = 0f

    private var touchTolerance = 10
    private var activeColor = Color.RED

    private val drawInfos = mutableListOf<DrawInfo>()
    private var initialized = false

    init {
        touchTolerance = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    activeColor = getNewColor()
                    offSet = INCREMENT
                    centerX = x
                    centerY = y
                    prevX = x
                    prevY = y
                    initialized = true
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = abs(prevX - x)
                    val dy = abs(prevY - y)
                    if (dx >= touchTolerance || dy >= touchTolerance) {
                        prevX = x
                        prevY = y
                        offSet += INCREMENT
                        invalidate()
                    }
                }
                MotionEvent.ACTION_UP -> {
                    drawInfos.add(DrawInfo(centerX, centerY, offSet, activeColor))
                }
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (!initialized)
            return
        canvas?.apply {
            drawInfos.forEach { it ->
                drawRect(this, it.centerX, it.centerY, it.offSet, it.activeColor)
            }
            drawRect(this, centerX, centerY, offSet)
        }
    }

    private fun drawRect(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        offSet: Int,
        color: Int = activeColor
    ) {
        val startX = (centerX - offSet / 2).toInt()
        val startY = (centerY - offSet / 2).toInt()
        rect.set(
            startX, startY, startX + offSet, startY + offSet
        )
        paint.color = color
        canvas.drawRect(rect, paint)
    }


    private fun getNewColor(): Int = Color.argb(
        255, random.nextInt(256),
        random.nextInt(256), random.nextInt(256)
    )


    data class DrawInfo(
        val centerX: Float,
        val centerY: Float,
        val offSet: Int,
        val activeColor: Int
    )
}