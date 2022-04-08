package com.wtfcompany.relax.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.FileDescriptor
import java.io.FileNotFoundException


fun decodeUri(context: Context, uri: Uri, size: Int): Bitmap? {
    try {
        val parcelFD = context.contentResolver.openFileDescriptor(uri, "r")
        val imageSource: FileDescriptor = parcelFD!!.fileDescriptor

        //Decode image size
        val o = BitmapFactory.Options()
        o.inJustDecodeBounds = true
        BitmapFactory.decodeFileDescriptor(imageSource, null, o)

        //Find the correct scale value. It should be the power of 2. 2???
        var width_tmp = o.outWidth
        var height_tmp = o.outHeight
        var scale = 1
        while (true) {
            if (width_tmp < size && height_tmp < size) {
                break
            }
            width_tmp /= 2
            height_tmp /= 2
            scale *= 2
        }

        //decode with inSampleSize
        val o2 = BitmapFactory.Options()
        o2.inSampleSize = scale
        return BitmapFactory.decodeFileDescriptor(imageSource, null, o2)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
    return null
}

fun bitmapToString(bitmap: Bitmap): String {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)
    return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
}

fun stringToBitmap(encodedString: String): Bitmap? {
    return try {
        val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    } catch (e: Exception) {
        null
    }
}