package com.frndzcode.client.example_room_hilt.ui.activity

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.frndzcode.client.example_room_hilt.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import androidx.navigation.ui.setupActionBarWithNavController
import com.frndzcode.client.example_room_hilt.utils.NetworkLiveStatusUtils
import com.frndzcode.client.example_room_hilt.utils.custom.isGone
import com.frndzcode.client.example_room_hilt.utils.custom.isVisible
import com.frndzcode.client.example_room_hilt.utils.custom.showDLog
import com.frndzcode.client.example_room_hilt.utils.custom.showELog
import com.frndzcode.client.example_room_hilt.utils.helper.NetworkStatus
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private var navController: NavController? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var connectionLiveStatus: NetworkLiveStatusUtils

    private val navigationDestListener =
        NavController.OnDestinationChangedListener{ _,destination,_->
            back_fragment.visibility = View.GONE
            notification_icon_head.isGone()
            setting_right.isGone()
            header_text.isGone()

            when(destination.id) {
                R.id.homeFragment -> {
                    header.isVisible()
                    header_text.isVisible()
                    header_text.text = "Room Database"
                    notification_icon_head.isVisible()
                    setting_right.isVisible()
                }

                R.id.notificationFragment -> {
                    header.isVisible()
                    header_text.isVisible()
                    header_text.text = "Notifications"
                    back_fragment.isVisible()
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindActivity()
        initListener()
    }

    private fun bindActivity() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavigationFragment) as NavHostFragment
        navController = navHostFragment.navController
        setupController(navController)

//setup internet connection have or not
        connectionLiveStatus = NetworkLiveStatusUtils(this)
        connectionLiveStatus.observe(this){
            showDLog("status - $it")
            when(it){
                true -> {
                    network_status.text = "Network Connection Established"
                    network_status.setBackgroundResource(R.color.green)
                    //network_status.isVisible()
                }

                false -> {
                    network_status.text = "No Internet"
                    network_status.setBackgroundResource(R.color.red)
                    //network_status.isVisible()
                }
            }

            /*when(it){
                NetworkStatus.Available -> {
                    network_status.text = "Network Connection Established"
                    network_status.setBackgroundResource(R.color.green)
                    //network_status.isVisible()
                }

                NetworkStatus.Unavailable -> {
                    network_status.text = "No Internet"
                    network_status.setBackgroundResource(R.color.red)
                   // network_status.isVisible()
                }
            }*/
        }
    }

    private fun setupController(navController: NavController?) {
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.notificationFragment
                )
        )

        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // connect nav controller with Toolbar
        setupActionBarWithNavController(
            navController!!,
            appBarConfiguration
        )

        navController.addOnDestinationChangedListener(navigationDestListener)   //on nav controller destination change listener
    }

    private fun initListener() {

    }
}