package com.n7elite.gaming.data

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs

/** Real values read from the phone. Null means "Android did not give us this value". */
data class DeviceSnapshot(
    val model: String,
    val androidVersion: String,
    val totalRamMb: Long,
    val availRamMb: Long,
    val totalStorageGb: Double,
    val freeStorageGb: Double,
    val batteryPercent: Int?,
    val batteryTempC: Float?
)

object DeviceReader {
    fun read(context: Context): DeviceSnapshot {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mem = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mem)

        val stat = StatFs(Environment.getDataDirectory().path)
        val gb = 1024.0 * 1024.0 * 1024.0

        val battery: Intent? = context.registerReceiver(
            null, IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )
        val level = battery?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = battery?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        val percent = if (level >= 0 && scale > 0) level * 100 / scale else null
        val tempRaw = battery?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, Int.MIN_VALUE)
        val temp = if (tempRaw != null && tempRaw != Int.MIN_VALUE) tempRaw / 10f else null

        return DeviceSnapshot(
            model = "${Build.MANUFACTURER} ${Build.MODEL}",
            androidVersion = "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})",
            totalRamMb = mem.totalMem / (1024 * 1024),
            availRamMb = mem.availMem / (1024 * 1024),
            totalStorageGb = stat.totalBytes / gb,
            freeStorageGb = stat.availableBytes / gb,
            batteryPercent = percent,
            batteryTempC = temp
        )
    }
}
