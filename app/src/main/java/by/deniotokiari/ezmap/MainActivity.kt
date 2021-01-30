package by.deniotokiari.ezmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.deniotokiari.ezmap.databinding.ActivityMainBinding
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinExperimentalAPI

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainActivityViewModel by viewModel()

    @KoinExperimentalAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()

        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }
}