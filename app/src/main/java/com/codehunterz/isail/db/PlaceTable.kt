package com.codehunterz.isail.db

import android.provider.BaseColumns

object PlaceTable : BaseColumns {
    const val PLACE_TABLE_NAME  = "place_table"
    const val COLUMN_ID   = "_id"

    // Place ID column (TEXT)
    const val COLUMN_PLACE_ID = "placeId"
    const val COLUMN_PLACE_ID_TYPE = "TEXT"

    // Name column (TEXT)
    const val COLUMN_NAME = "name"
    const val COLUMN_NAME_TYPE = "TEXT"

    // Icon column (TEXT)
    const val COLUMN_ICON = "icon"
    const val COLUMN_ICON_TYPE = "TEXT"

    // Latitude column (REAL)
    const val COLUMN_LAT  = "latitude"
    const val COLUMN_LAT_TYPE = "REAL"

    // Longitude column (REAL)
    const val COLUMN_LNG  = "longitude"
    const val COLUMN_LNG_TYPE = "REAL"
}
