package com.workshop.kmp.platform

import platform.UIKit.UIDevice

actual object PlatformInfo {
    actual val platformName: String = UIDevice.currentDevice.systemName()
    actual val osVersion: String = UIDevice.currentDevice.systemVersion()
}
