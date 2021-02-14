package by.deniotokiari.feature.app.controls

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import by.deniotokiari.core.tiles.provider.FilesViewModel
import by.deniotokiari.feature.app.controls.databinding.FragmentMenuBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MenuBottomSheetFragment(
    private val filesViewModel: FilesViewModel
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var observer: MyLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observer = MyLifecycleObserver(requireActivity().activityResultRegistry) {
            filesViewModel.addFiles(it)

            dismiss()
        }
        lifecycle.addObserver(observer)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return FragmentMenuBinding.inflate(inflater, container, false)
            .also { binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addTiles.setOnClickListener {
            observer.selectFiles()
        }
    }
}

private class MyLifecycleObserver(private val registry: ActivityResultRegistry, private val callback: (List<Uri>) -> Unit) : DefaultLifecycleObserver {

    private lateinit var getContent: ActivityResultLauncher<String>

    override fun onCreate(owner: LifecycleOwner) {
        getContent = registry.register("key", owner, ActivityResultContracts.GetMultipleContents()) {
            callback(it)
        }
    }

    fun selectFiles() {
        getContent.launch("*/*")
    }
}
