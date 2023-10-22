package river.exertion.sac.swe

enum class CalcUtDatas {
    LONGITUDE_DATA, LATITUDE_DATA, DISTANCE_DATA, LONG_SPEED_DATA, LAT_SPEED_DATA, DIST_SPEED_DATA;

    companion object {
        val HOUSE_DATA_IDX = run { entries.size }
        val TRANSIT_HOUSE_DATA_IDX = run { entries.size + 1 }
        val SIZE = run { entries.size }
        val EXT_SIZE = run { entries.size + 2 }
    }
}

