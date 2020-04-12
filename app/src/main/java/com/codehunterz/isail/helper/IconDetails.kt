package com.codehunterz.isail.helper

import android.os.Build

object IconDetails {
    private val iconDetailsMap: HashMap<String?, String?> =
        object : HashMap<String?, String?>() {
            init {
                put("nfl_anchorage", "a place to anchor your vessel/yacht/boat")
                put("nfl_harbour", "a harbour")
                put("nfl_marina", "a marina")
                put("nfl_shop", "a shop")
                put("nfl_fuel", "a place to fuel up")
                put("nfl_restaurant", "a restaurant")
                put("nfl_dinghyDock", "a dinghy dock for short time docking")
                put("nfl_touristAttraction", "a tourist attraction")
                put("nfl_boatService", "a boat service")
                put("nfl_navInfo", "a navigation information place")
                put("nfl_boatYard", "a boat yard")
                put("nfl_transport", "a place to find transportation")
                put("nfl_hurricaneHole", "a hurricane hole")
                put("nfl_natureReserve", "a nature reserve")
                put("nfl_navWarning", "a navigational warning")
                put("nfl_water", "a place to get water")
                put("nfl_dive", "a place to snorkel and dive")
                put("nfl_portOfEntryOffice" , "a port of entry")
                put("nfl_mooringBuoys" , "a place to park your vessel through a buoy")
                put("nfl_wasteDisposal" , "a waste disposal")
                put("nfl_airport" , "an airport")
                put("nfl_chandler" , "a ship store - a chandlery")

            }
        }

    fun getDetails(iconName: String): String {
        val thisIs = "This is "
        val unknown = "an unknown place"
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            thisIs + iconDetailsMap.getOrDefault(iconName, unknown)
        else thisIs + unknown
    }
}