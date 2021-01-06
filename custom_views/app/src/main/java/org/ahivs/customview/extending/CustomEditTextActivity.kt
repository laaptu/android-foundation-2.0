package org.ahivs.customview.extending

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.ahivs.customview.R

class CustomEditTextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customedit_text)
    }
}