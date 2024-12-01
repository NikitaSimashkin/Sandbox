package ru.kram.sandbox.features.compose.effect

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
internal fun SideEffectScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        var state by remember { mutableStateOf(SideEffectScreenState.LaunchedEffect) }
        var user: User? by remember { mutableStateOf(null) }

        val analytics = when (state) {
            SideEffectScreenState.LaunchedEffect -> {
                user?.let { rememberAnalyticsLaunchedEffect(user = it) }
            }

            SideEffectScreenState.SideEffect -> {
                user?.let { rememberAnalyticsSideEffect(user = it) }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    state = SideEffectScreenState.LaunchedEffect
                }
            ) {
                Text(text = "LaunchedEffect")
            }
            Button(
                onClick = {
                    state = SideEffectScreenState.SideEffect
                }
            ) {
                Text(text = "SideEffect")
            }
        }

        Text(text = "State = $state")

        Text(text = "User type: ${user?.userType}")

        Button(
            onClick = {
                user = User(Random.nextInt(1000).toString())
            }
        ) {
            Text(text = "Update user")
        }

        Button(
            onClick = {
                Toast.makeText(
                    context,
                    analytics?.getUserProperty("userType") ?: "No user property",
                    Toast.LENGTH_SHORT
                ).show()
            }
        ) {
            Text(text = "Get user property")
        }
    }
}

@Composable
fun rememberAnalyticsSideEffect(user: User): Analytics {
    val analytics: Analytics = remember { AnalyticsImpl() }
    SideEffect {
        // Called after every successful recomposition

        // В этом случае конечно можем заменить на LaunchedEffect
        // Но если нету ключа (user в данном случае), и надо
        // что то делать при рекомпозиции, тогда SideEffect
        analytics.setUserProperty("userType", user.userType)
    }
    return analytics
}

@Composable
fun rememberAnalyticsLaunchedEffect(user: User): Analytics {
    val analytics: Analytics = remember { AnalyticsImpl() }
    LaunchedEffect(user) {
        analytics.setUserProperty("userType", user.userType)
    }
    return analytics
}

enum class SideEffectScreenState {
    LaunchedEffect,
    SideEffect,
}

interface Analytics {
    val properies: HashMap<String, String>

    fun setUserProperty(key: String, value: String) {
        properies[key] = value
    }

    fun getUserProperty(key: String): String {
        return "User property $key is ${properies[key]}"
    }
}

class AnalyticsImpl : Analytics {
    override val properies: HashMap<String, String> = hashMapOf()
}

data class User(val userType: String)