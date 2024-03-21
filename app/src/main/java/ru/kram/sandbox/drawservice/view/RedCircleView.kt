package ru.kram.sandbox.drawservice.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class RedCircleView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

	private val paint = Paint().apply {
		color = Color.RED
	}

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)

		val centerX = width / 2f
		val centerY = height / 2f
		val radius = minOf(centerX, centerY)

		canvas.drawCircle(centerX, centerY, radius, paint)
	}
}