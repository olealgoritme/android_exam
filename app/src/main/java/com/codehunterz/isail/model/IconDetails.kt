package com.codehunterz.isail.model

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