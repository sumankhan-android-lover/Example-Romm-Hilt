package com.frndzcode.client.example_room_hilt.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.frndzcode.client.example_room_hilt.R
import com.frndzcode.client.example_room_hilt.app.MyConstants.SOCIAL_MEDIA.FACEBOOK
import com.frndzcode.client.example_room_hilt.utils.NetworkLiveStatusUtils
import com.frndzcode.client.example_room_hilt.utils.custom.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.json.JSONException

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private var navController: NavController? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var connectionLiveStatus: NetworkLiveStatusUtils
    private var socialLoginListener = { isSuccess: Boolean, loginUsingType: String,
                                        socialLoginFName: String, socialLoginLName: String,
                                        socialLoginEmail: String, socialLoginImage: String,
                                        socialAuthKey: String,socialId : String -> Unit }

    //facebook
    private lateinit var fbCallBackManager: CallbackManager
    private val fbLoginInstance = LoginManager.getInstance()


    private var loginType: String = ""
    private var socialLoginName = ""
    private var socialLoginEmail = ""
    private var socialLoginImage = ""
    private var socialAuthKey = ""
    private var socialId = ""



    //handle toolbar
    private val navigationDestListener =
        NavController.OnDestinationChangedListener{ _,destination,_->
            back_fragment.makeGone()
            notification_icon_head.isGone()
            setting_right.isGone()
            header_text.isGone()

            when(destination.id) {
                R.id.homeFragment -> {
                    header.makeVisible()
                    header_text.makeVisible()
                    header_text.text = "Room Database"
                    notification_icon_head.makeVisible()
                    setting_right.makeVisible()
                }

                R.id.notificationFragment -> {
                    header.makeVisible()
                    header_text.makeVisible()
                    header_text.text = "Notifications"
                    back_fragment.makeVisible()
                    notification_icon_head.makeGone()
                    setting_right.makeGone()
                }

                R.id.settingsFragment -> {
                    header.makeVisible()
                    header_text.makeVisible()
                    header_text.text = "Settings"
                    back_fragment.makeVisible()
                    setting_right.makeGone()
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindActivity()
        initListener()
        initFaceBookLogin()
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
        }
    }

    private fun setupController(navController: NavController?) {
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.notificationFragment,
                R.id.settingsFragment
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
        notification_icon_head.setOnClickListener {
            navController?.navigate(R.id.notificationFragment)
        }

        setting_right.setOnClickListener {
            navController?.navigate(R.id.settingsFragment)
        }

        back_fragment.setOnClickListener {
            onBackPressed()
        }
    }

    //social media

    //facebook
    fun callFacebookLogin(
        listener: (
            isSuccess: Boolean, loginUsingType: String, socialLoginFName: String, socialLoginLName: String,
            socialLoginEmail: String, socialLoginImage: String, socialAuthKey: String,socialId:String
        ) -> Unit
    ) {
        socialLoginListener = listener
        loginType = FACEBOOK
        fbLoginInstance.logInWithReadPermissions(
            this,
            listOf("public_profile", "email")
        )
    }

    private fun initFaceBookLogin() {
        fbCallBackManager = CallbackManager.Factory.create()

        val call = object : FacebookCallback<LoginResult> {

            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {
                TODO("Not yet implemented")
            }

            override fun onSuccess(result: LoginResult) {
                val graphQuery =
                    GraphRequest.newMeRequest(result.accessToken) { object1, response ->
                        try {
                            showELog("object 1 : $object1 :: response : $response")
                            val fnm = object1?.getString("first_name")?.toString()
                            val lnm = object1?.getString("last_name")?.toString()
                            val fid = object1?.getString("id")
                            val photo = object1?.getJSONObject("picture")?.getJSONObject("data")?.getString("url")
                            socialLoginName = "$fnm $lnm"
                            socialLoginImage = photo.toString()//"https://graph.facebook.com/$fid/picture?type=large"
                            socialAuthKey = AccessToken.getCurrentAccessToken()?.token.toString()
                            socialId = fid.toString()
                            loginType = FACEBOOK

                            socialLoginEmail = if (object1!!.has("email"))
                                object1.getString("email").trim()
                            else
                                ""

                            fbLoginInstance.logOut()
                            socialLoginListener(
                                true,
                                loginType,
                                fnm.toString(),
                                lnm.toString(),
                                socialLoginEmail,
                                socialLoginImage,
                                socialAuthKey,
                                socialId
                            )
                        } catch (e: JSONException) {
                            Log.e("exception", "fb" + e.message)
                        }
                    }
                val parameters = Bundle()
                parameters.putString(
                    "fields",
                    "id, first_name, last_name, email, picture  "
                )
                graphQuery.parameters = parameters
                graphQuery.executeAsync()
            }

        }
        fbLoginInstance.registerCallback(fbCallBackManager, call)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("resultCode",""+resultCode)

        if (resultCode == Activity.RESULT_OK) {
            when (loginType) {
                FACEBOOK -> fbCallBackManager.onActivityResult(
                    requestCode,
                    resultCode,
                    data
                )
            }
        }

        navHostFragment.let { navFragment ->
            navFragment.childFragmentManager.primaryNavigationFragment?.onActivityResult(
                requestCode,
                resultCode,
                data
            )
        }

    }
}