package com.frndzcode.client.example_room_hilt.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.frndzcode.client.example_room_hilt.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import androidx.navigation.ui.setupActionBarWithNavController

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private var navController: NavController? = null
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val navigationDestListener =
        NavController.OnDestinationChangedListener{ _,destination,_->
            back_fragment.visibility = View.GONE
            notification_icon_head.visibility = View.GONE
            setting_right.visibility = View.GONE
            header_text.visibility = View.GONE

            when(destination.id) {
                R.id.homeFragment -> {
                    header.visibility = View.VISIBLE
                    header_text.visibility = View.VISIBLE
                    header_text.text = "Room Database"
                }

                R.id.notificationFragment -> {
                    header.visibility = View.VISIBLE
                    header_text.visibility = View.VISIBLE
                    header_text.text = "Notifications"
                    back_fragment.visibility = View.VISIBLE
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


    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("On Activity", "Result $requestCode  $resultCode $data")
        super.onActivityResult(requestCode, resultCode, data)

        navHostFragment.let { navFragment ->
            navFragment.childFragmentManager.primaryNavigationFragment?.onActivityResult(
                requestCode,
                resultCode,
                data
            )
        }
    }*/
}