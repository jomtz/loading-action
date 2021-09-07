package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.provider.Settings.Global.getString
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var widthSize = 0
    private var heightSize = 0

    private var buttonText: String
    private var buttonColor = R.attr.buttonColor
    private val valueAnimator = ValueAnimator()

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Loading -> {
                setButtonText("@string/button_loading")
                setButtonColor("#004349")
            }
            ButtonState.Clicked -> {}
            ButtonState.Completed -> {}
        }
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LoadingButton,
            0, 0).apply {

            try {
                buttonText = getString(R.styleable.LoadingButton_buttonText).toString()
                buttonColor = ContextCompat.getColor(context, R.color.colorPrimary)
            } finally {
                recycle()
            }
        }

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(buttonColor)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    /**
     *  Setter property for text on Custom Loading Button
     */
    private fun setButtonText(buttonText: String) {
        this.buttonText = buttonText
        invalidate()
        requestLayout()
    }

    /**
     *  Setter property for color on Custom Loading Button
     */
    private fun setButtonColor(backgroundColor: String) {
        buttonColor = Color.parseColor(backgroundColor)
        invalidate()
        requestLayout()
    }

}