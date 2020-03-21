package com.codehunterz.isail.db

import com.codehunterz.isail.model.places.Place

class Misc {
/*
    fun insert(place : Place) {
            // Place Properties
            val placeId = place.getProperties()?.id
            val placeName =  place.getProperties()?.name
            val placeIcon = place.getProperties()?.icon

            // Place geometry
            val placeLat = place.geometry?.coordinates?.get(0) // Lat
            val placeLng = place.geometry?.coordinates?.get(1) // Lng

        val query = "INSERT INTO $PLACE_TABLE_NAME ($COLUMN_PLACE_ID, $COLUMN_NAME, $COLUMN_ICON, $COLUMN_LAT, $COLUMN_LNG) VALUES ($placeId, $placeName, $placeIcon, $placeLat, $placeLng)"
        DatabaseHelper.getInstance(context)!!.writableDatabase.execSQL(query)
    }

    // This is a performance function :-))
    fun insertList(placeList : List<Place>) {

        // Begin transaction
        val queryBegin = "BEGIN TRANSACTION;"
        DatabaseHelper.getInstance(context)!!.writableDatabase.execSQL(queryBegin)

        // Iterate place list
        placeList.forEach { place ->
            insert(place)
        }

        // End Transaction
        val queryEnd = "END TRANSACTION;"
        DatabaseHelper.getInstance(context)!!.writableDatabase.execSQL(queryEnd)
    }


    fun fetchAll(): List<Place> {

        val cursor: Cursor = DatabaseHelper.getInstance(context)!!.readableDatabase.query(PLACE_TABLE_NAME, null, null, null, null, null, null)
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
        return placeList
    }

*/


    }