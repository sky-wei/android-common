/*
 * Copyright (c) 2021 The sky Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.android.core.util

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import com.sky.android.common.util.Alog
import java.io.File

/**
 * Created by sky on 2021-06-28.
 */
object PatchUtil {

    fun repairMediaUri(context: Context?, uri: Uri?) {

        if (context == null || uri == null
            || MediaStore.AUTHORITY != uri.authority) {
            return
        }

        var cursor: Cursor? = null

        try { // 获取媒体信息
            val contentResolver = context.contentResolver
            cursor = contentResolver.query(
                uri,
                arrayOf(
                    MediaStore.MediaColumns.DISPLAY_NAME,
                    MediaStore.MediaColumns.DATA
                ),
                null,
                null,
                null
            )
            if (cursor != null && cursor.moveToFirst()) { // 获取文件信息
                val displayName = cursor.getString(
                    cursor.getColumnIndex(
                        MediaStore.MediaColumns.DISPLAY_NAME
                    )
                )
                val filePath = cursor.getString(
                    cursor.getColumnIndex(
                        MediaStore.MediaColumns.DATA
                    )
                )
                if (TextUtils.isEmpty(displayName) && !TextUtils.isEmpty(filePath)) {
                    val file = File(filePath)
                    val contentValues = ContentValues()
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, file.name)
                    // 更新媒体信息
                    contentResolver.update(uri, contentValues, null, null)
                }
            }
        } catch (tr: Throwable) {
            Alog.e("修复异常", tr)
        } finally {
            cursor?.close()
        }
    }
}