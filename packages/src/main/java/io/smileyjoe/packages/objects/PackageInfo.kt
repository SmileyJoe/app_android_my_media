package io.smileyjoe.packages.objects

import android.graphics.drawable.Drawable

data class PackageInfo(
    val id: String,
    val name: String,
    val isInstalled: Boolean,
    val icon: Drawable?
) {
    val playstoreUrl: String
        get() = "http://play.google.com/store/apps/details?id=$id"
}
