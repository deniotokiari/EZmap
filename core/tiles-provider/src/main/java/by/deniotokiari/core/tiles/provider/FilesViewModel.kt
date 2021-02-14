package by.deniotokiari.core.tiles.provider

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.util.StorageUtils
import java.io.File

class FilesViewModel(
    private val context: Context,
    private val tilesProvider: TilesProvider<File>
) : ViewModel() {

    private val _files: MutableLiveData<List<File>> = MutableLiveData()
    val files: LiveData<List<File>> by this::_files

    init {
        viewModelScope.launch(Dispatchers.IO) {
            refreshFiles()
        }
    }

    private fun refreshFiles() {
        _files.postValue(tilesProvider.findFiles().toList())
    }

    fun addFiles(items: List<Uri>) {
        viewModelScope.launch(Dispatchers.IO) {
            val path = StorageUtils.getBestWritableStorage(context).path

            items.forEach { uri ->
                uri.lastPathSegment?.split("/")?.last()?.let { "$path${File.separator}$it" }?.let { name ->
                    context.contentResolver.openInputStream(uri)?.use { input ->
                        File(name).outputStream().use { out ->
                            input.copyTo(out)
                        }
                    }
                }
            }

            refreshFiles()
        }
    }
}