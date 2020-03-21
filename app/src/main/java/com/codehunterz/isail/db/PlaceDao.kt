package com.codehunterz.isail.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteStatement
import com.codehunterz.isail.db.PlaceTable.COLUMN_ICON
import com.codehunterz.isail.db.PlaceTable.COLUMN_LAT
import com.codehunterz.isail.db.PlaceTable.COLUMN_LNG
import com.codehunterz.isail.db.PlaceTable.COLUMN_NAME
import com.codehunterz.isail.db.PlaceTable.COLUMN_PLACE_ID
import com.codehunterz.isail.db.PlaceTable.PLACE_TABLE_NAME
import com.codehunterz.isail.model.places.Geometry
import com.codehunterz.isail.model.places.Place
import com.codehunterz.isail.model.places.Property


class PlaceDao(private var ctx: Context) {

    private var db = DatabaseHelper.getInstance(ctx)

    fun insert(place : Place): Long? {
            // Place Properties
            val placeId = place.getProperties()?.id
            val placeName =  place.getProperties()?.name
            val placeIcon = place.getProperties()?.icon

            // Place geometry
            val placeLat = place.geometry?.coordinates?.get(0) // Lat
            val placeLng = place.geometry?.coordinates?.get(1) // Lng

        val values = ContentValues().apply {
            put(COLUMN_PLACE_ID, placeId)
            put(COLUMN_NAME, placeName)
            put(COLUMN_ICON, placeIcon)
            put(COLUMN_LAT, placeLat)
            put(COLUMN_LNG, placeLng)
        }

        this.db!!.writableDatabase.use {
            it.insertOrThrow(PLACE_TABLE_NAME, null, values)
        }
        return 0;
    }

    // This is a performance function :-))
    fun insertList(placeList : List<Place>) {

        // Begin transaction
        db!!.writableDatabase.beginTransaction()

        val query = "INSERT OR IGNORE INTO $PLACE_TABLE_NAME ($COLUMN_PLACE_ID, $COLUMN_NAME, $COLUMN_ICON, $COLUMN_LAT, $COLUMN_LNG) VALUES (?, ?, ?, ?, ?)"
        val stmt: SQLiteStatement = db!!.writableDatabase.compileStatement(query)

        // Iterate place list
        placeList.forEach { place ->
            // Properties
            stmt.run {

                // Properties
                bindString(1, place.getProperties()?.id)
                bindString(2, place.getProperties()?.name)
                bindString(3, place.getProperties()?.icon)

                // Geometry
                bindDouble(4, place.geometry!!.coordinates!![0])
                bindDouble(5, place.geometry!!.coordinates!![1])
                stmt.execute()
                stmt.clearBindings()
            }
        }

        // Set successful flag
        db!!.writableDatabase.setTransactionSuccessful()

        // End Transaction
        db!!.writableDatabase.endTransaction()


        // Close DB
        db!!.writableDatabase.close()

    }

    fun fetchAll(): List<Place> {

        val cursor: Cursor = db!!.readableDatabase.query(PLACE_TABLE_NAME, null, null, null, null, null, null)
        val placeList = mutableListOf<Place>()

        with(cursor) {
            while (moveToNext()) {
                val placeId = getString(getColumnIndexOrThrow(COLUMN_PLACE_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val icon = getString(getColumnIndexOrThrow(COLUMN_ICON))
                val lat = getDouble(getColumnIndexOrThrow(COLUMN_LAT))
                val lng = getDouble(getColumnIndexOrThrow(COLUMN_LNG))
                val properties: Property? = Property(placeId, name, icon)
                val geometry: Geometry? = Geometry(lat, lng)
                placeList.add(Place(properties, geometry))
            }
        }
        db!!.writableDatabase.close()
        return placeList
    }

    fun delete(id: Long) {

    }
}





