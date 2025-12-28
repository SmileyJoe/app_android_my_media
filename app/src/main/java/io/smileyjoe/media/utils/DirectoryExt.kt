package io.smileyjoe.media.utils

import android.content.Context
import androidx.documentfile.provider.DocumentFile
import io.smileyjoe.media.exception.FileNotWriteableException
import io.smileyjoe.media.models.FileInfo
import okio.FileNotFoundException

fun DocumentFile.info(): FileInfo? =
    name?.let {
        FileInfo(
            name = it,
            size = 0
        )
    }

suspend fun DocumentFile.write(context: Context, contents: String) =
    uri.write(context, contents)

fun DocumentFile.createIfNotExists(
    mimeType: String,
    fileName: String,
    created: (document: DocumentFile) -> Unit
) {
    findFile(fileName)?.let {
        if (!it.canWrite()) {
            throw FileNotWriteableException()
        }
    } ?: run {
        createFile(mimeType, fileName.substring(0, fileName.lastIndexOf(".")))?.let {
            created(it)
        } ?: run {
            throw FileNotFoundException()
        }
    }
}