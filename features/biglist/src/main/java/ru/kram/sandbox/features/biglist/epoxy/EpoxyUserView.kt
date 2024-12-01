package ru.kram.sandbox.features.biglist.epoxy

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.bumptech.glide.Glide
import ru.kram.sandbox.features.biglist.databinding.UserItemBinding
import ru.kram.sandbox.features.biglist.presentation.model.UserListItem

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, saveViewState = true)
internal class EpoxyUserView @JvmOverloads constructor(
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
    var user: UserListItem.UserUi? = null

    @set:CallbackProp
    var onClick: ((UserListItem.UserUi) -> Unit)? = null

    private val vb = UserItemBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    @AfterPropsSet
    fun bind() {
        val user = user!!

        vb.name.text = user.name
        vb.age.text = user.age.toString()
        vb.surname.text = user.surname
        Glide.with(vb.root)
            .load(user.avatarUrl)
            .into(vb.avatar)

        setOnClickListener { onClick?.invoke(user) }
    }

    @OnViewRecycled
    fun clear() {
        user = null
    }
}