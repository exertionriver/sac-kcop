import river.exertion.sac.astro.CelestialSnapshot
import river.exertion.sac.astro.EarthLocation

object TestEarthLocations {

    val atxEarthLocation = EarthLocation("atx", "-97.7611", "30.2504","167", "UTC-7", "2014-09-03T12:09:03" )
    val sfeEarthLocation = EarthLocation("sfe", "-105.9576", "35.6691","2116", "UTC-8", "2019-09-09T09:09:00" )
    val tnmEarthLocation = EarthLocation("tnm", "-105.5785", "36.3791","2135", "UTC-8", "2021-10-09T18:10:09" )

    val atxCelestialSnapshotNatComp = CelestialSnapshot(atxEarthLocation)
    val sfeCelestialSnapshotNatComp = CelestialSnapshot(sfeEarthLocation)
    val tnmCelestialSnapshotNatComp = CelestialSnapshot(tnmEarthLocation)

    val atxCelestialSnapshotSyn = CelestialSnapshot(atxEarthLocation, sfeEarthLocation)
    val sfeCelestialSnapshotSyn = CelestialSnapshot(sfeEarthLocation, tnmEarthLocation)
    val tnmCelestialSnapshotSyn = CelestialSnapshot(tnmEarthLocation, atxEarthLocation)

}