package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.WHITE
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.content_main.view.*
import kotlin.properties.Delegates


class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var widthSize = 0
    private var heightSize = 0

    private var buttonText: String
    private var buttonColor = R.attr.buttonColor
    private var movingValue: Float = 0f
    private val textRect = Rect()

    private var valueAnimator = ValueAnimator()


    private var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { _, _, new ->
        when (new) {
            ButtonState.Loading -> {
                setButtonText("We are loading")
                setButtonColor("#004349")
                valueAnimator= ValueAnimator.ofFloat(0f, 1f).apply {
                    addUpdateListener {
                        movingValue = animatedValue as Float
                        invalidate()
                    }
                    duration = 3000
                    start()
                }
                stopLoadingButton()
            }

            ButtonState.Completed -> {
                setButtonText("Download")
                setButtonColor("#07C2AA")
                valueAnimator.cancel()
                initialMovingValue()
                startLoadingButton()
            }

            ButtonState.Clicked -> {}
        }
        invalidate()
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

    /**
     *  textPaint handle "How to draw" the text on Loading Button
     */
    private val textPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = WHITE
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 60.0f
    }

    /**
     *  backgroundPaint Handle "How to draw" the background on Loading Button
     */
    private val backgroundPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.colorPrimary)
    }
    /**
     *  movingBackgroundPaint Handle "How to draw" the background on a
     *  moving state Loading Button
     */
    private val movingBackgroundPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
    }
    /**
     *  movingArcPaint Handle "How to draw" the 360 degrees state Loading Button
     */
    private val movingArcPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.colorAccent)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cornerRadius = 10.0f
        val backgroundWidth = measuredWidth.toFloat()
        val backgroundHeight = measuredHeight.toFloat()

        canvas.drawColor(buttonColor)
        textPaint.getTextBounds(buttonText, 0, buttonText.length, textRect)
        canvas.drawRoundRect(0f, 0f, backgroundWidth, backgroundHeight, cornerRadius,
            cornerRadius, backgroundPaint)

        if (buttonState == ButtonState.Loading) {
            var movingVal = movingValue * backgroundWidth

            canvas.drawRoundRect(0f, 0f, movingVal, backgroundHeight, cornerRadius,
                cornerRadius, movingBackgroundPaint)

            val arcDiameter = cornerRadius * 2
            val arcRectSize = measuredHeight.toFloat() - paddingBottom.toFloat() - arcDiameter

            movingVal = movingValue * 360f
            canvas.drawArc( arcDiameter, arcDiameter, arcRectSize, arcRectSize,
                0f, movingVal,true, movingArcPaint)
        }

        val centerX = this.measuredWidth.toFloat() / 2
        val centerY = this.measuredHeight.toFloat() / 2 - textRect.centerY()
        canvas.drawText(buttonText, centerX, centerY, textPaint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minimumWidth: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minimumWidth, widthMeasureSpec, 1)
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
     *  Properties to start and stop Loading Button
     */
    private fun startLoadingButton() {
        custom_button.isEnabled = true
    }
    private fun stopLoadingButton() {
        custom_button.isEnabled = false
    }

    private fun initialMovingValue() {
        movingValue = 0f
    }

    /**
     *  Setter property for buttonState
     */
    fun setCustomButtonState(state: ButtonState) {
        buttonState = state
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