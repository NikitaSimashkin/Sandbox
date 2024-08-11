package ru.kram.sandbox.biglist.epoxy

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import ru.kram.sandbox.biglist.presentation.model.UserListItem
import ru.kram.sandbox.databinding.ColorItemBinding

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT, saveViewState = true)
class EpoxyColorView @JvmOverloads constructor(
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
    var color: UserListItem.Color? = null

    private val vb = ColorItemBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    @AfterPropsSet
    fun bind() {
        vb.colorItem.setBackgroundColor(color!!.value)
    }

    @OnViewRecycled
    fun clear() {
        color = null
    }
}