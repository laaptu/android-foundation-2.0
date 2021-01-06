package org.ahivs.customview.basic

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import org.ahivs.customview.R
import kotlin.math.ceil


class Rectangle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : View(context, attrs, defStyleAttrs) {

    val paint: Paint = Paint()
    val rectMain: Rect = Rect()
    val textPaint: Paint = Paint()
    var text: String = ""
    val textXY: PointF = PointF()


    init {
        with(paint) {
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        with(textPaint) {
            style = Paint.Style.FILL
            isAntiAlias = true
            typeface = Typeface.MONOSPACE
            color = Color.WHITE
            textSize = context.resources.getDimensionPixelSize(R.dimen.txtSize).toFloat()
        }
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomRect, 0, 0
        )
            .apply {
                try {
                    val color = getColor(R.styleable.CustomRect_rectColor, Color.RED)
                    paint.color = color


                    text = getString(R.styleable.CustomRect_rectText) ?: "NONE"
                    val textSize = getDimension(
                        R.styleable.CustomRect_rectTextSize,
                        textPaint.textSize
                    )
                    textPaint.textSize = textSize
                } finally {
                    recycle()
                }
            }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectMain.set(
            paddingLeft,
            paddingTop,
            w - paddingRight,
            h - paddingBottom
        )

        val fontMetrics: Paint.FontMetrics = textPaint.fontMetrics
        val textWidth = ceil(textPaint.measureText(text).toDouble()).toFloat()
        val textHeight = ceil(-fontMetrics.top + fontMetrics.bottom)
        textXY.set(
            (rectMain.exactCenterX() - textWidth / 2.0).toFloat(),
            (rectMain.exactCenterY() - textHeight / 2.0).toFloat()
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawRect(rectMain, paint)
            drawText(text, textXY.x, textXY.y, textPaint)
        }
    }
}