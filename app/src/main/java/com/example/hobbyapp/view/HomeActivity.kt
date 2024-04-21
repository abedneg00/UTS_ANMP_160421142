package com.example.hobbyapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hobbyapp.R
import com.example.hobbyapp.databinding.ActivityHomeBinding

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController

class HomeActivity : AppCompatActivity() {
//    companion object {
//        private var instance:HomeActivity ?= null
//
//        fun showNotification(title:String, content:String, icon:Int) { //kenapa icon nya int? karena kita ambil id yang sudah di set
//            val channelId = "${instance?.packageName}-${instance?.getString(R.string.app_name)}"
//
//            //CREATE NOTIF MENGGUNAKAN BUILDER
//            // builder wajib berisi context. double tanda seru menunjukkan bahwa builder pasti tidak null
//            val builder = NotificationCompat.Builder(instance!!.applicationContext, channelId).apply {
//                setSmallIcon(icon)
//                setContentTitle(title)
//                setContentText(content)
//                setStyle(NotificationCompat.BigTextStyle())
//                priority = NotificationCompat.PRIORITY_DEFAULT
//                setAutoCancel(true)
//            }
//
//            val manager = NotificationManagerCompat.from(instance!!.applicationContext)
//            if (ActivityCompat.checkSelfPermission(instance!!.applicationContext,
//                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(instance!!,
//                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),1) //memaksa rquest permission ke user
//                //requestPermissions diatas parameter pertama butuh activity
//                return
//            }
//            manager.notify(1001, builder.build())  //ini coding penting untuk menampilkan notif ke layar
//        }
//    }

    private lateinit var navController: NavController
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController = (supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment).navController //cara setup navController
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout) //menyuruh navcontroller meng handle drawer
        NavigationUI.setupWithNavController(binding.navView, navController)

        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        //function yang icon kiri atas di tekan
        //akan navigate ke backstage di bawahnya
        //return navController.navigateUp()
        return NavigationUI.navigateUp(navController, binding.drawerLayout) || super.onSupportNavigateUp()
    }
}