import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun AnimatedVector2DScreen() {

    var startAnimation by remember { mutableStateOf(false) }
    var isSpringEnabled by remember { mutableStateOf(false) }

    val spring = remember<AnimationSpec<MySize>> {
        spring(
            dampingRatio = 0.5f,
            stiffness = 100f
        )
    }

    val tween = remember<AnimationSpec<MySize>> {
        tween(durationMillis = 3000)
    }

    val animation = animateValueAsState(
        targetValue = if (!startAnimation) MySize(50.dp, 50.dp) else MySize(200.dp, 400.dp),
        typeConverter = MySizeConverter,
        animationSpec = if (isSpringEnabled) spring else tween,
        label = ""
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(
                    width = animation.value.width,
                    height = animation.value.height
                )
                .background(Color.Red)
                .align(Alignment.Center)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    startAnimation = !startAnimation
                }
        )

        Button(
            onClick = {
                isSpringEnabled = !isSpringEnabled
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .width(200.dp)
                .height(50.dp)
        ) {
            Text(text = "Set ${if (isSpringEnabled) "tween" else "spring"} animation")
        }
    }
}

data class MySize(
    val width: Dp,
    val height: Dp
)

object MySizeConverter : TwoWayConverter<MySize, AnimationVector2D> {
    override val convertFromVector: (AnimationVector2D) -> MySize
        get() = { animationVector2D ->
            MySize(
                width = animationVector2D.v1.dp,
                height = animationVector2D.v2.dp
            )
        }

    override val convertToVector: (MySize) -> AnimationVector2D
        get() = { mySize ->
            AnimationVector2D(
                v1 = mySize.width.value,
                v2 = mySize.height.value
            )
        }
}