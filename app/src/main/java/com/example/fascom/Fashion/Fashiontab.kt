package com.example.fascom.Fashion

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.fascom.Addtocart
import com.example.fascom.R
import kotlinx.android.synthetic.main.activity_healthtab.*
import kotlinx.android.synthetic.main.activity_household.*
import kotlinx.android.synthetic.main.activity_household.tablay
import kotlinx.android.synthetic.main.defappbar.*

class Fashiontab : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthtab)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.pinkti)
        }

        val adapter = MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Men(), "Men")
        adapter.addFragment(Women(), "Women")
        fashionviewpager.adapter = adapter
        tablay.setupWithViewPager(fashionviewpager)
        backbut.setOnClickListener {
            finish()
        }

        bag.setOnClickListener {
            val intent = Intent(this@Fashiontab, Addtocart::class.java)
            startActivity(intent)
        }


    }

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titlelist: MutableList<String> = ArrayList()

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        fun addFragment(fragment: Fragment, title: String){
            fragmentList.add(fragment)
            titlelist.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titlelist[position]
        }
    }
}