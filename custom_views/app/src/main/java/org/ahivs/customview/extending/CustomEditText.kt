package org.ahivs.customview.extending

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import org.ahivs.customview.R

class CustomEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatEditText(context, attrs, defStyle) {

    private var clearButtonImage: Drawable? = null

    init {
        updateClearButtonImage(R.drawable.ic_clear_black_24)
        addTextChangeListener()
        addTouchListener()
    }

    private fun addTextChangeListener() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (length() == 0)
                        hideClearButton()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showClearButton()
            }
        })
    }

    private fun addTouchListener() {
        setOnTouchListener { v, event ->
            if (compoundDrawables[2] != null) {
                val isClearBtnClicked = if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                    val clearBtnEnd = width + paddingStart
                    event.x < clearBtnEnd
                } else {
                    val clearBtnStart =
                        (width - paddingEnd - (clearButtonImage?.intrinsicWidth ?: 0))
                    event.x > clearBtnStart
                }
                if (isClearBtnClicked) {
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        updateClearButtonImage(R.drawable.ic_clear_transparent_24)
                        showClearButton()
                    }

                    if (event.action == MotionEvent.ACTION_UP) {
                        updateClearButtonImage(R.drawable.ic_clear_black_24)
                        text?.clear()
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }

    private fun updateClearButtonImage(drawableId: Int) {
        clearButtonImage = ResourcesCompat.getDrawable(resources, drawableId, null)
    }

    private fun showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            null, null,
            clearButtonImage,
            null
        )
    }

    private fun hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            null, null, null, null
        )
    }
}