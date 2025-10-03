package io.smileyjoe.media.utils

import android.content.Intent

val Intent.createFile: Intent
    get() = filePicker(Intent.ACTION_CREATE_DOCUMENT)

val Intent.chooseFile: Intent
    get() = filePicker(Intent.ACTION_OPEN_DOCUMENT)

private fun filePicker(action: String) =
    Intent.createChooser(
        Intent(action).apply {
            setType("*/*")
            addCategory(Intent.CATEGORY_OPENABLE)
        },
        "Text"
    )