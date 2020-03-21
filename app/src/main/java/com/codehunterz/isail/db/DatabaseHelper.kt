package com.codehunterz.isail.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.codehunterz.isail.db.PlaceTable.COLUMN_ICON
import com.codehunterz.isail.db.PlaceTable.COLUMN_ICON_TYPE
import com.codehunterz.isail.db.PlaceTable.COLUMN_ID
import com.codehunterz.isail.db.PlaceTable.COLUMN_LAT
import com.codehunterz.isail.db.PlaceTable.COLUMN_LAT_TYPE
import com.codehunterz.isail.db.PlaceTable.COLUMN_LNG
import com.codehunterz.isail.db.PlaceTable.COLUMN_LNG_TYPE
import com.codehunterz.isail.db.PlaceTable.COLUMN_NAME
import com.codehunterz.isail.db.PlaceTable.COLUMN_NAME_TYPE
import com.codehunterz.isail.db.PlaceTable.COLUMN_PLACE_ID
import com.codehunterz.isail.db.PlaceTable.COLUMN_PLACE_ID_TYPE

open class DatabaseHelper(ctx: Context) : SQLiteOpenHelper(
    ctx,
    DATABASE_NAME,
    null,
    DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val querySQL = "CREATE TABLE ${PlaceTable.PLACE_TABLE_NAME} " +
                "(" +
                "$COLUMN_ID INTEGER PRIMARY KEY, " +
                " $COLUMN_PLACE_ID $COLUMN_PLACE_ID_TYPE NOT NULL UNIQUE, " +
                " $COLUMN_NAME $COLUMN_NAME_TYPE, " +
                " $COLUMN_ICON $COLUMN_ICON_TYPE," +
                " $COLUMN_LAT $COLUMN_LAT_TYPE," +
                " $COLUMN_LNG $COLUMN_LNG_TYPE" +
                ")"
        db.execSQL(querySQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        private var mInstance: DatabaseHelper? = null
        private const val DATABASE_NAME = "places_database"
        private const val DATABASE_VERSION = 1

        fun getInstance(ctx: Context): DatabaseHelper? {
            if (mInstance == null)
                mInstance = DatabaseHelper(ctx.applicationContext)

            return mInstance
        }
    }

}