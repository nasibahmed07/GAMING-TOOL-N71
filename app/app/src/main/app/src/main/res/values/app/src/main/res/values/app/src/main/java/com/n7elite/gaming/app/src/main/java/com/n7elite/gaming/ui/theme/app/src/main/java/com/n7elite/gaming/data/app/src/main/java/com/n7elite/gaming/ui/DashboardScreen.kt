package com.n7elite.gaming.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.n7elite.gaming.data.DeviceReader
import com.n7elite.gaming.ui.theme.N7Black
import com.n7elite.gaming.ui.theme.N7Blue
import com.n7elite.gaming.ui.theme.N7Charcoal
import com.n7elite.gaming.ui.theme.N7Red
import com.n7elite.gaming.ui.theme.N7TextDim

@Composable
fun DashboardScreen() {
    val context = LocalContext.current
    var snapshot by remember { mutableStateOf(DeviceReader.read(context)) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(N7Black, N7Charcoal)))
            .statusBarsPadding(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text(
                    "N7 ELITE",
                    color = N7Red,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 4.sp
                )
                Text("Gaming control panel", color = N7Blue, fontSize = 13.sp, letterSpacing = 2.sp)
            }
        }

        item {
            GlassCard(title = "ACTIVE PROFILE") {
                Text("Balanced Player", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Default profile. Profile editing arrives in Stage 3.",
                    color = N7TextDim, fontSize = 12.sp
                )
            }
        }

        item {
            GlassCard(title = "DEVICE") {
                InfoRow("Model", snapshot.model)
                InfoRow("System", snapshot.androidVersion)
                InfoRow("RAM free", "${snapshot.availRamMb} MB of ${snapshot.totalRamMb} MB")
                InfoRow(
                    "Storage free",
                    "%.1f GB of %.1f GB".format(snapshot.freeStorageGb, snapshot.totalStorageGb)
                )
                InfoRow("Battery", snapshot.batteryPercent?.let { "$it%" } ?: "Unavailable")
                InfoRow(
                    "Battery temp",
                    snapshot.batteryTempC?.let { "%.1f °C".format(it) } ?: "Unavailable"
                )
                InfoRow("Live FPS", "Not measurable by this app")
                Button(
                    onClick = { snapshot = DeviceReader.read(context) },
                    colors = ButtonDefaults.buttonColors(containerColor = N7Red),
                    modifier = Modifier.padding(top = 10.dp)
                ) { Text("Refresh") }
            }
        }

        item {
            GlassCard(title = "MODULES") {
                ModuleRow("Sensitivity Manager", "Stage 2")
                ModuleRow("Gaming Profiles", "Stage 3")
                ModuleRow("HUD Configurator", "Stage 4")
                ModuleRow("Aim Training", "Stage 5")
                ModuleRow("Recoil Simulator", "Stage 6")
                ModuleRow("Device Performance", "Stage 7")
            }
        }
    }
}

@Composable
private fun GlassCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x33FFFFFF)),
        border = BorderStroke(1.dp, Brush.linearGradient(listOf(N7Red, N7Blue)))
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, color = N7Blue, fontSize = 12.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Bold)
            Column(Modifier.padding(top = 8.dp)) { content() }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = N7TextDim, fontSize = 13.sp)
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun ModuleRow(name: String, stage: String) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name, fontSize = 14.sp)
        Text("Coming in $stage", color = N7TextDim, fontSize = 12.sp)
    }
}
