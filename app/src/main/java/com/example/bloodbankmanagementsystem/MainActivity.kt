package com.example.bloodbankmanagementsystem

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.bloodbankmanagementsystem.adapter.ViewPagerAdapter
import com.example.bloodbankmanagementsystem.fragments.BloodStockActivity
import com.example.bloodbankmanagementsystem.fragments.DashboardActivity
import com.example.bloodbankmanagementsystem.fragments.EventActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val permissions = arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private var counter = 0L
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var lstTitle: ArrayList<String>
    private lateinit var lstFragments: ArrayList<Fragment>
    private lateinit var viewpager2: ViewPager2
    private lateinit var tablayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewpager2 = findViewById(R.id.viewpager)
        tablayout = findViewById(R.id.tablayout)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer)
        val navView: NavigationView = findViewById(R.id.navmenu)

        // check for permission
        if (!hasPermission()) {
            requestPermission()
        }
        populateList()
        val adapter = ViewPagerAdapter(lstFragments, supportFragmentManager, lifecycle)
        viewpager2.adapter = adapter
        TabLayoutMediator(tablayout, viewpager2) { tab, position ->
            tab.text = lstTitle[position]
        }.attach()

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }

                R.id.nav_profile -> {
                    Toast.makeText(this, "profile clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_donateblood -> {
                    Toast.makeText(this, "donateblood clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_map -> {

                    Toast.makeText(this, "map clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_contact -> {

                    Toast.makeText(this, "contact clicked", Toast.LENGTH_SHORT).show()
                }

                R.id.nav_rateapp -> {
                    Toast.makeText(this, "feedback clicked", Toast.LENGTH_SHORT).show()

                }
                R.id.nav_logout -> {
                    logout()
                }
            }

            true

        }
    }

    private fun populateList() {
        lstTitle=ArrayList<String>()
        lstTitle.add("DashBoard")
        lstTitle.add("Blood Stock")
        lstTitle.add("Events")
        lstFragments=ArrayList<Fragment>()
        lstFragments.add(DashboardActivity())
        lstFragments.add(BloodStockActivity())
        lstFragments.add(EventActivity())
    }

    private fun logout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Log Out!!")
        builder.setMessage("Are you sure do you want to Logout ?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Yes") {_,_ ->
            userlogout()
        }
        builder.setNegativeButton("No") {_,_ ->
            Toast.makeText(this,"Cancelled", Toast.LENGTH_SHORT).show()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun userlogout() {
        val sharedPref = this.getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this@MainActivity,
            permissions, 1
        )
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
            }
        }
        return hasPermission

    }

    override fun onBackPressed() {

        if (counter + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            finish()
        }
        else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        counter = System.currentTimeMillis()
    }
}