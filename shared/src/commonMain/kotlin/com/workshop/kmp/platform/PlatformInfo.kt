package com.workshop.kmp.platform

/**
 * expect/actual: Jede Plattform liefert ihren eigenen Namen und die OS-Version.
 * shared-Code kann PlatformInfo nutzen, ohne zu wissen, ob er auf Android oder iOS läuft.
 */
expect object PlatformInfo {
    val platformName: String
    val osVersion: String
}
