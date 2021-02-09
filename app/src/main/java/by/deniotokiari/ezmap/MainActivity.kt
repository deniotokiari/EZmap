package by.deniotokiari.ezmap

import android.os.Bundle
import android.view.View
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

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        viewModel?.navigation?.consume(this) {
            navigationController.navigate(it)
        }
    }
}