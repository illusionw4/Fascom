package com.example.fascom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.defappbar.*

class Healthtab : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthtab)

//        val adapter = MyViewPagerAdapter(supportFragmentManager)
//        adapter.addFragment(Juices(), "Juices")
//        adapter.addFragment(Shakes(), "Shakes")
//        adapter.addFragment(Healthysnacks(), "Healthy Snacks")
//        viewpager.adapter = adapter
//        tablay.setupWithViewPager(viewpager)

        backbut.setOnClickListener {
            finish()
        }
        bag.setOnClickListener {
            val intent = Intent(this@Healthtab, Addtocart::class.java)
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