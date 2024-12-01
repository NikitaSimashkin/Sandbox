package ru.kram.sandbox.features.carousel.carousel1;

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Created by nbtk on 5/4/18.
 */
internal class SliderLayoutManager(context: Context?) : LinearLayoutManager(context) {

    init {
         orientation = VERTICAL
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        scaleDownView()
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
		val scrolled = super.scrollVerticallyBy(dy, recycler, state)
		scaleDownView()
		return scrolled
    }

    private fun scaleDownView() {
        val mid = height / 2.0f
        for (i in 0 until childCount) {

            // Calculating the distance of the child from the center
            val child = getChildAt(i) ?: return
            val childMid = (getDecoratedTop(child) + getDecoratedBottom(child)) / 2.0f
            val distanceFromCenter = abs(mid - childMid)

            // The scaling formula
            val scale = 1- sqrt((distanceFromCenter/height).toDouble()).toFloat() * 0.5f

            // Set scale to view
            child.scaleX = scale
            child.scaleY = scale
        }
    }
}