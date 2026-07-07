package com.workshop.kmp.platform

import android.os.Build

actual object PlatformInfo {
    actual val platformName: String = "Android"
    actual val osVersion: String = "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
}
