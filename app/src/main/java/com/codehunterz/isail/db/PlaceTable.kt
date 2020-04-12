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

    // CountryCode column (TEXT)
    const val COLUMN_COUNTRY_CODE = "country_code"
    const val COLUMN_COUNTRY_CODE_TYPE = "TEXT"

    // Comments column (TEXT)
    const val COLUMN_COMMENTS = "comments"
    const val COLUMN_COMMENTS_TYPE = "TEXT"

    // Stars column (REAL)
    const val COLUMN_STARS = "stars"
    const val COLUMN_STARS_TYPE = "REAL"

    // Banner column (TEXT)
    const val COLUMN_BANNER = "banner"
    const val COLUMN_BANNER_TYPE = "TEXT"

    // Images column (TEXT)
    const val COLUMN_IMAGES_JSON = "images_json"
    const val COLUMN__IMAGES_JSON_TYPE = "TEXT"
}
