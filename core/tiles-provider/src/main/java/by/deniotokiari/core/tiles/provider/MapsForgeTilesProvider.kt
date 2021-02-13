package by.deniotokiari.core.tiles.provider

import android.content.Context
import org.osmdroid.tileprovider.util.StorageUtils
import java.io.File

class MapsForgeTilesProvider(private val context: Context) : TilesProvider<File> {

    override fun findFiles(): Array<File> {
        return StorageUtils
            .getStorageList(context)
            .mapNotNull { File(it.path + File.separator).listFiles { file -> file.name.endsWith(".map") }?.toList() }
            .flatten()
            .toTypedArray()
    }
}