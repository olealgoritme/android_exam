package com.codehunterz.isail.helper

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.net.Uri

@SuppressLint("Registered")
class ImageProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        autoContext = context
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Nothing? = null

    override fun getType(uri: Uri): Nothing? = null

    override fun insert(
        uri: Uri,
        values: ContentValues?
    ): Nothing? = null

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ) = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ) = 0

    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmField
        var autoContext: Context? = null
    }
}