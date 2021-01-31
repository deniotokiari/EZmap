package by.deniotokiari.ezmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import by.deniotokiari.core.navigation.MainNavigation
import by.deniotokiari.ezmap.databinding.ActivityMainBinding
import by.deniotokiari.utils.android.consume
import org.koin.android.ext.android.get
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.core.KoinExperimentalAPI

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navigationController: NavController by lazy { findNavController(R.id.nav_host_fragment) }
    private val viewModel: MainActivityViewModel? by lazy { get<MainNavigation>() as? MainActivityViewModel }

    @KoinExperimentalAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()

        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        viewModel?.navigation?.consume(this) {
            navigationController.navigate(it)
        }
    }
}