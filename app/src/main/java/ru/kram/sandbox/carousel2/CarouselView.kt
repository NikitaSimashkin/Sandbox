package ru.kram.sandbox.carousel2

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.core.view.isVisible
import ru.kram.sandbox.R

class CarouselView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

	private var topView: TextView? = createView()
	private var bottomView: TextView? = createView()

	private val scrollAnimationInterpolator = PathInterpolatorCompat.create(0f, 0f, 0.58f, 1f)

	init {
		init()
		bottomView?.isVisible = false
	}

	fun scroll(newText: String) {

		val verticalOffset = height - (topView?.y ?: 0f)

		bottomView?.apply {
			text = newText
			scaleX = DEFAULT_SCALE
			scaleY = DEFAULT_SCALE
			alpha = DEFAULT_ALPHA
			translationY = verticalOffset
			isVisible = true
			animate().apply {
				interpolator = scrollAnimationInterpolator
				duration = DEFAULT_ANIMATION_DURATION_MS
				translationY(0f)
				withStartAction {
					Log.d(TAG, "start bottom animation")
				}
			}
		}

		topView?.animate()?.apply {
			interpolator = scrollAnimationInterpolator
			duration = DEFAULT_ANIMATION_DURATION_MS
			scaleX(MIN_SCALE)
			scaleY(MIN_SCALE)
			translationY(-verticalOffset)
			alpha(MIN_ALPHA)
			withStartAction {
				Log.d(TAG, "start top animation")
			}
		}

		topView = bottomView?.also { bottomView = topView }
	}

	private fun init() {
		addView(topView)
		addView(bottomView)
	}

	private fun createView(): TextView {
		val view = LayoutInflater.from(context).inflate(R.layout.scroll_view_item, this, false) as TextView
		view.layoutParams = LayoutParams(view.layoutParams).apply {
			gravity = Gravity.CENTER
		}
		view.paint.shader = LinearGradient(
			0f, 0f, 0f, view.lineHeight.toFloat(),
			intArrayOf(0xB7B8FDFD.toInt(), 0xB3FFFFFF.toInt(), 0xC2A7A0F9.toInt()),
			floatArrayOf(0f, 0.507f, 1f),
			Shader.TileMode.CLAMP
		)
		return view
	}

	companion object {
		private const val TAG = "CarouselView"

		private const val MIN_SCALE = 0.85f
		private const val DEFAULT_SCALE = 1f

		private const val MIN_ALPHA = 0f
		private const val DEFAULT_ALPHA = 1f

		private const val DEFAULT_ANIMATION_DURATION_MS = 800L
	}
}