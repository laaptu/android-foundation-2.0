package org.ahivs.customview.intermediate

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import org.ahivs.customview.R
import org.ahivs.customview.databinding.ActivitySimpledrawBinding

class SimpleDrawActivity : Activity() {


    private lateinit var canvas: Canvas
    private val paint: Paint = Paint()
    private val paintText: Paint = Paint(Paint.UNDERLINE_TEXT_FLAG)
    private lateinit var bitmap: Bitmap
    private var rect: Rect = Rect()
    private var boundRect: Rect = Rect()

    private val OFFSET = 120
    private var offSet = OFFSET
    private val MULTIPLIER = 100
    private var done = false

    private var colorBackground = 0
    private var colorRectangle = 0
    private var colorAccent = 0

    private lateinit var binding: ActivitySimpledrawBinding
    private lateinit var imgView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpledrawBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imgView = binding.imgView

        colorBackground = fetchColor(R.color.colorBackground)
        colorRectangle = fetchColor(R.color.colorRectangle)
        colorAccent = fetchColor(R.color.colorAccent)

        paint.color = colorBackground
        paintText.apply {
            color = fetchColor(R.color.colorPrimaryDark)
            textSize = 70f
        }
    }

    fun drawSomething(view: View) {
        val width = imgView.width
        val height = imgView.height

        val widthHalf = width / 2
        val heightHalf = height / 2

        if (offSet == OFFSET) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            imgView.setImageBitmap(bitmap)
            canvas = Canvas(bitmap)
            canvas.drawColor(colorBackground)
            canvas.drawText(getString(R.string.keep_tapping), 100f, 100f, paintText)
            offSet += OFFSET
            imgView.invalidate()
        } else {
            if (offSet < widthHalf && offSet < heightHalf) {
                paint.color = colorRectangle - MULTIPLIER * offSet
                rect.set(offSet, offSet, width - offSet, height - offSet)
                canvas.drawRect(rect, paint)
                offSet += OFFSET
                imgView.invalidate()
            } else if (!done) {
                paint.color = colorAccent
                canvas.drawCircle(widthHalf.toFloat(), heightHalf.toFloat(), widthHalf / 2f, paint)
                val text = getString(R.string.done)
                paintText.getTextBounds(text, 0, text.length, boundRect)

                val x = widthHalf - boundRect.exactCenterX()
                val y = heightHalf - boundRect.exactCenterY()
                canvas.drawText(text, x, y, paintText)
                imgView.invalidate()
                done = true
            }
        }

    }

    private fun fetchColor(id: Int): Int = ResourcesCompat.getColor(resources, id, null)
}