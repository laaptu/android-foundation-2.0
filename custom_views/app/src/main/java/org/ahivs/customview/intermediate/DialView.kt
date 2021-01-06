package org.ahivs.customview.intermediate

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import org.ahivs.customview.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class DialView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    View(context, attrs, defStyle) {

    private val SELECTION_COUNT = 4
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.FILL_AND_STROKE
        textAlign = Paint.Align.CENTER
        textSize = 40f
    }

    private val dialPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
    }

    private var activeSelection = 0
    private var radius = 0f
    private var labelRadius = 0f
    private var markerRadius = 0f
    private val label = StringBuffer()

    private val mainRect = Rect()

    private var fanOnColor: Int = Color.GREEN
    private var fanOffColor: Int = Color.GRAY

    init {

        context.theme.obtainStyledAttributes(attrs, R.styleable.DialView, 0, 0)
            .apply {
                try {
                    fanOnColor = getColor(R.styleable.DialView_fanOnColor, fanOnColor)
                    fanOffColor = getColor(R.styleable.DialView_fanOffColor, fanOffColor)
                } finally {
                    recycle()
                }
            }
        setOnClickListener {
            activeSelection = (activeSelection + 1) % SELECTION_COUNT
            if (activeSelection > 0)
                dialPaint.color = fanOnColor
            else
                dialPaint.color = fanOffColor
            invalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val dimen = min(w, h)
        mainRect.set(paddingLeft, paddingTop, dimen - paddingRight, dimen - paddingBottom)
        radius = (dimen / 2 * 0.8).toFloat()
        labelRadius = radius + 20
        markerRadius = radius - 35
    }

    private fun computeXYForPosition(pos: Int, radius: Float): FloatArray {
        val startAngle: Double = PI * (1.14)
        val angle: Double = startAngle + (pos * PI / 4)
        return floatArrayOf(
            (radius * cos(angle) + (mainRect.width() / 2)).toFloat(),
            (radius * sin(angle) + (mainRect.width() / 2)).toFloat()
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            //big circle
            drawCircle(mainRect.exactCenterX(), mainRect.exactCenterY(), radius, dialPaint)
            //draw text labels
            for (i in 0 until SELECTION_COUNT) {
                val xyPos = computeXYForPosition(i, labelRadius)
                val x = xyPos[0]
                val y = xyPos[1]
                label.setLength(0)
                label.append(i)
                drawText(label, 0, label.length, x, y, textPaint)
            }

            //draw the selected indicator
            val xyPosMarker = computeXYForPosition(activeSelection, markerRadius)
            drawCircle(xyPosMarker[0], xyPosMarker[1], 20f, textPaint)
        }
    }
}