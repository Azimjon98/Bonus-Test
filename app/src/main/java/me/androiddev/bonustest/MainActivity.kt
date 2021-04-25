package me.androiddev.bonustest

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import me.androiddev.core.common.ConnectivityLiveData
import me.androiddev.logintestapp.util.UiHelper


class MainActivity(val layoutId: Int = R.layout.activity_main) : AppCompatActivity() {

    companion object {
        val NAV_HOST_FRAGMENT_TAG = "navGraph"
    }

    lateinit var progressBar: View

    private val currentNavController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        progressBar = findViewById(R.id.progress)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        supportActionBar?.hide()
        val conManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        val liveData = ConnectivityLiveData(this)
        liveData.observe(this){
            currentNavController.currentBackStackEntry?.savedStateHandle?.set("connection", true)
        }
    }


    override fun onSupportNavigateUp(): Boolean =
        currentNavController.navigateUp() || super.onSupportNavigateUp()

    fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    fun disableUserInteraction() {
        UiHelper.disableMyWindow(this)
        showProgress()
    }

    fun enableUserInteraction() {
        UiHelper.enableMyWindow(this)
        hideProgress()
    }
}