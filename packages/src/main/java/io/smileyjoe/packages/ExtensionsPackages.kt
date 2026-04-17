package io.smileyjoe.packages

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import io.smileyjoe.packages.objects.PackageInfo

/**
 * Get all installed applications as a list of [AppDetail]
 *
 * @param packageManager
 * @return list of installed applications
 */
@SuppressLint("QueryPermissionsNeeded")
fun PackageManager.getInstalled(): List<PackageInfo> {
    return getInstalledPackages(PackageManager.PackageInfoFlags.of(0))
        .filter { packageInfo ->
            packageInfo.applicationInfo?.let {
                it.flags and ApplicationInfo.FLAG_SYSTEM == 0
            } ?: false
        }
        .map { packageInfo ->
            return@map PackageInfo(
                id = packageInfo.packageName,
                name = packageInfo.applicationInfo!!.loadLabel(this).toString(),
                isInstalled = true,
                icon = getIcon(packageInfo.packageName)
            )
        }
        .sortedBy { it.name }
}

/**
 * Get the icon from the package manager
 *
 * @param appPackage the package name
 * @return the icon for the installed app
 */
fun PackageManager.getIcon(appPackage: String?): Drawable? =
    appPackage?.let {
        runCatching {
            getApplicationIcon(it)
        }.getOrNull()
    }

fun PackageManager.getIntent(id: String): Intent? {
    val intent = getLaunchIntentForPackage(id)

    return intent?.resolveActivity(this)?.let {
        intent
    }
}

fun PackageManager.isInstalled(id: String): Boolean =
    runCatching {
        getPackageInfo(id, 0)?.let {
            true
        }
    }.getOrNull()
        ?: run {
            false
        }