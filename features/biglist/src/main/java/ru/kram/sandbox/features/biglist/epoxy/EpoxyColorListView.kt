package ru.kram.sandbox.features.biglist.epoxy

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import ru.kram.sandbox.features.biglist.databinding.ListColorItemBinding
import ru.kram.sandbox.features.biglist.presentation.color.ColorsAdapter
import ru.kram.sandbox.features.biglist.presentation.model.UserListItem
import timber.log.Timber

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, saveViewState = true)
internal class EpoxyColorListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): FrameLayout(
    context,
    attrs,
    defStyleAttr,
    defStyleRes
) {

    @set:ModelProp
    var colorList: UserListItem.ColorList? = null

    @set:CallbackProp
    var onClick: ((UserListItem.ColorList) -> Unit)? = null

    private val vb = ListColorItemBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    private val lm = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    @AfterPropsSet
    fun bind() {
        val colorList = colorList!!

        vb.colorList.adapter = ColorsAdapter().apply {
            submitList(colorList.list)
        }
        vb.colorList.layoutManager = lm
    }

    @OnViewRecycled
    fun clear() {
        colorList = null
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        Timber.tag("EpoxyBigList").d("onVisibilityChanged: $visibility")
    }

    override fun onVisibilityAggregated(isVisible: Boolean) {
        super.onVisibilityAggregated(isVisible)
        Timber.tag("EpoxyBigList").d("onVisibilityAggregated: $isVisible")
    }
}