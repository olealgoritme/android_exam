package com.codehunterz.isail.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteStatement
import com.codehunterz.isail.App
import com.codehunterz.isail.db.PlaceTable.COLUMN_BANNER
import com.codehunterz.isail.db.PlaceTable.COLUMN_COMMENTS
import com.codehunterz.isail.db.PlaceTable.COLUMN_COUNTRY_CODE
import com.codehunterz.isail.db.PlaceTable.COLUMN_ICON
import com.codehunterz.isail.db.PlaceTable.COLUMN_IMAGES_JSON
import com.codehunterz.isail.db.PlaceTable.COLUMN_LAT
import com.codehunterz.isail.db.PlaceTable.COLUMN_LNG
import com.codehunterz.isail.db.PlaceTable.COLUMN_NAME
import com.codehunterz.isail.db.PlaceTable.COLUMN_PLACE_ID
import com.codehunterz.isail.db.PlaceTable.COLUMN_STARS
import com.codehunterz.isail.db.PlaceTable.PLACE_TABLE_NAME
import com.codehunterz.isail.model.places.Place.Geometry
import com.codehunterz.isail.model.places.Place
import com.codehunterz.isail.model.places.Place.Property

class PlaceDao {

    // Using static App context reference in case DB handling is happening between context switches
    private var db: DBInstance = DBInstance.getInstance(App.getContext())!!


    fun update(placeId: Long, countryCode: String, comments: String, stars: Int, banner: String, imagesJson: String) : Int {

        val values = ContentValues().apply {
            put(COLUMN_COUNTRY_CODE, countryCode)
            put(COLUMN_COMMENTS, comments)
            put(COLUMN_STARS, stars)
            put(COLUMN_BANNER, banner)
            put(COLUMN_IMAGES_JSON, imagesJson)
        }

        return db.writableDatabase.use {
            it.update(PLACE_TABLE_NAME, values, "WHERE placeID = {$placeId}", null)
        }
    }

    // This is a performance function :-))
    fun insertList(placeList : List<Place>) {

        // Begin transaction
        db.writableDatabase.beginTransaction()

        val query =
            "INSERT OR IGNORE INTO $PLACE_TABLE_NAME ($COLUMN_PLACE_ID, $COLUMN_NAME, $COLUMN_ICON, $COLUMN_LAT, $COLUMN_LNG) VALUES (?, ?, ?, ?, ?)"
        val stmt: SQLiteStatement = db.writableDatabase.compileStatement(query)

        // Iterate place list
        placeList.forEach { place ->
            // Properties
            stmt.run {

                // Properties
                bindLong(1, place.properties.id)
                bindString(2, place.properties.name)
                bindString(3, place.properties.icon)

                // Geometry
                bindDouble(4, place.geometry.coordinates[1])
                bindDouble(5, place.geometry.coordinates[0])

                stmt.execute()
                stmt.clearBindings()
            }
        }

        // Set successful flag
        db.writableDatabase.setTransactionSuccessful()

        // End Transaction
        db.writableDatabase.endTransaction()

        // Close DB
        db.writableDatabase.close()
    }

    fun fetchAll(): MutableList<Place> {

        val cursor: Cursor = db.readableDatabase.query(PLACE_TABLE_NAME, null, null, null, null, null, null)
        val placeList = mutableListOf<Place>()

        with(cursor) {
            while (moveToNext()) {
                val placeId:   Long   = getLong(getColumnIndexOrThrow(COLUMN_PLACE_ID))
                val placeName: String = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val placeIcon: String = getString(getColumnIndexOrThrow(COLUMN_ICON))
                val placeLat:  Double = getDouble(getColumnIndexOrThrow(COLUMN_LAT))
                val placeLng:  Double = getDouble(getColumnIndexOrThrow(COLUMN_LNG))

                val properties = Property(placeId, placeName, placeIcon)
                val geometry = Geometry(listOf(placeLat, placeLng))
                val place = Place(properties, geometry)
                placeList.add(place)
            }
        }
        db.writableDatabase.close()
        return placeList
    }
}





