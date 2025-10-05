package io.smileyjoe.media.utils

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import io.smileyjoe.media.models.FileInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

suspend fun Uri.read(context: Context): String =
    withContext(Dispatchers.IO) {
        val stringBuilder = StringBuilder()
        context.contentResolver.openInputStream(this@read)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        stringBuilder.toString()
    }

suspend fun Uri.write(context: Context, contents: String): Boolean =
    withContext(Dispatchers.IO) {
        context.contentResolver.openOutputStream(this@write)
            ?.bufferedWriter()
            ?.use { out ->
                out.write(contents)
                true
            } ?: false
    }

suspend fun Uri.info(context: Context): FileInfo? =
    withContext(Dispatchers.IO) {
        context.contentResolver.getCursor(this@info) { cursor ->
            cursor?.let {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                FileInfo(
                    name = cursor.getString(nameIndex),
                    size = cursor.getLong(sizeIndex)
                )
            }
        }
    }

private fun <T> ContentResolver.getCursor(uri: Uri, handle: (cursor: Cursor?) -> T?): T? =
    runCatching {
        query(
            uri,
            null,
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                handle(cursor)
            } else {
                handle(null)
            }
        }
    }.getOrNull()
