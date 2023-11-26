package river.exertion.sac.asset

import river.exertion.kcop.asset.AssetManagerHandler
import river.exertion.kcop.asset.IAssetStore
import river.exertion.kcop.asset.IAssetStoreCompanion
import river.exertion.sac.astro.base.EarthLocation

//Used for sac default file
enum class SACDefaultAssetStore(val path: String) : IAssetStore {
    Default("assetExt/sac/default.json"),
    EarthLocation0Default("assetExt/sac/el0.json"),
    EarthLocation1Default("assetExt/sac/el1.json"),
    EarthLocation2Default("assetExt/sac/el2.json"),
    EarthLocation3Default("assetExt/sac/el3.json"),
    EarthLocation4Default("assetExt/sac/el4.json"),
    EarthLocation5Default("assetExt/sac/el5.json"),
    EarthLocation6Default("assetExt/sac/el6.json"),
    EarthLocation7Default("assetExt/sac/el7.json"),
    EarthLocation8Default("assetExt/sac/el8.json"),
    EarthLocation9Default("assetExt/sac/el9.json"),
    ;
    override fun load() = AssetManagerHandler.loadAssetByPath<EarthLocation>(path)
    override fun get() = AssetManagerHandler.getAsset<EarthLocation>(path)

    companion object : IAssetStoreCompanion {
        override fun loadAll() = AssetManagerHandler.loadAssetsByPath<EarthLocation>(entries.map { it.path })
        override fun getAll() = AssetManagerHandler.getAssets<EarthLocation>(entries.map { it.path })
    }
}