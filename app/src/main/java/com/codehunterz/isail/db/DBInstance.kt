package com.codehunterz.isail.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.codehunterz.isail.db.PlaceTable.COLUMN_BANNER
import com.codehunterz.isail.db.PlaceTable.COLUMN_BANNER_TYPE
import com.codehunterz.isail.db.PlaceTable.COLUMN_COMMENTS
import com.codehunterz.isail.db.PlaceTable.COLUMN_COMMENTS_TYPE
import com.codehunterz.isail.db.PlaceTable.COLUMN_COUNTRY_CODE
import com.codehunterz.isail.db.PlaceTable.COLUMN_COUNTRY_CODE_TYPE
import com.codehunterz.isail.db.PlaceTable.COLUMN_ICON
import com.codehunterz.isail.db.PlaceTable.COLUMN_ICON_TYPE
import com.codehunterz.isail.db.PlaceTable.COLUMN_ID
import com.codehunterz.isail.db.PlaceTable.COLUMN_IMAGES_JSON
import com.codehunterz.isail.db.PlaceTable.COLUMN_LAT
import com.codehunterz.isail.db.PlaceTable.COLUMN_LAT_TYPE
import com.codehunterz.isail.db.PlaceTable.COLUMN_LNG
import com.codehunterz.isail.db.PlaceTable.COLUMN_LNG_TYPE
import com.codehunterz.isail.db.PlaceTable.COLUMN_NAME
import com.codehunterz.isail.db.PlaceTable.COLUMN_NAME_TYPE
import com.codehunterz.isail.db.PlaceTable.COLUMN_PLACE_ID
import com.codehunterz.isail.db.PlaceTable.COLUMN_PLACE_ID_TYPE
import com.codehunterz.isail.db.PlaceTable.COLUMN_STARS
import com.codehunterz.isail.db.PlaceTable.COLUMN_STARS_TYPE
import com.codehunterz.isail.db.PlaceTable.COLUMN__IMAGES_JSON_TYPE

open class DBInstance(ctx: Context) : SQLiteOpenHelper(
    ctx,
    DATABASE_NAME,
    null,
    DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createPlacesTableSQLString())       // Create places table
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    private fun createPlacesTableSQLString() : String {
        return "CREATE TABLE ${PlaceTable.PLACE_TABLE_NAME} " +
                "(" +
                "$COLUMN_ID INTEGER PRIMARY KEY, " +
                " $COLUMN_PLACE_ID $COLUMN_PLACE_ID_TYPE NOT NULL UNIQUE, " +
                " $COLUMN_NAME $COLUMN_NAME_TYPE, " +
                " $COLUMN_ICON $COLUMN_ICON_TYPE," +
                " $COLUMN_LAT $COLUMN_LAT_TYPE," +
                " $COLUMN_LNG $COLUMN_LNG_TYPE" +
                " $COLUMN_COUNTRY_CODE $COLUMN_COUNTRY_CODE_TYPE" +
                " $COLUMN_COMMENTS $COLUMN_COMMENTS_TYPE" +
                " $COLUMN_STARS $COLUMN_STARS_TYPE" +
                " $COLUMN_BANNER $COLUMN_BANNER_TYPE" +
                " $COLUMN_IMAGES_JSON $COLUMN__IMAGES_JSON_TYPE" +
                ")"
    }

    companion object {
        private var mInstance: DBInstance? = null
        private const val DATABASE_NAME = "places_database"
        private const val DATABASE_VERSION = 1

        fun getInstance(ctx: Context): DBInstance? {
            if (mInstance == null)
                mInstance = DBInstance(ctx.applicationContext)

            return mInstance
        }
    }

}