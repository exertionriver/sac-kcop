package river.exertion.sac.asset

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.files.FileHandle
import kotlinx.serialization.json.decodeFromJsonElement
import river.exertion.kcop.asset.AssetManagerHandler
import river.exertion.sac.astro.EarthLocation

class EarthLocationLoader(resolver: FileHandleResolver?) :
    AsynchronousAssetLoader<EarthLocation?, EarthLocationLoader.EarthLocationParameter?>(resolver) {

    lateinit var rawData: String

    override fun getDependencies(fileName: String?, file: FileHandle?, parameter: EarthLocationParameter?): com.badlogic.gdx.utils.Array<AssetDescriptor<Any>>? {
        return null
    }

    override fun loadAsync(manager: AssetManager, fileName: String, file: FileHandle, parameter: EarthLocationParameter?) {
    }

    override fun loadSync(manager: AssetManager, fileName: String, file: FileHandle, parameter: EarthLocationParameter?): EarthLocation {
        return try {
            rawData = file.readString()
            val jsonElement = AssetManagerHandler.json.parseToJsonElement(rawData)
            val earthLocation = AssetManagerHandler.json.decodeFromJsonElement(jsonElement) as EarthLocation

            earthLocation

        } catch (ex : Exception) {
            EarthLocation().apply {
//                this.assetStatus = AssetStatus(this.assetPath(), "asset not loaded", ex.message)
            }
        }
    }

    class EarthLocationParameter : AssetLoaderParameters<EarthLocation?>()
    }