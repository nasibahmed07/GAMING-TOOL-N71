package com.n7elite.gaming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.n7elite.gaming.ui.DashboardScreen
import com.n7elite.gaming.ui.theme.N7Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N7Theme {
                DashboardScreen()
            }
        }
    }
}
