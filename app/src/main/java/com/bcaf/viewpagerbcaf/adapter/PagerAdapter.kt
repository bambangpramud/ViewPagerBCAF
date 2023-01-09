package com.bcaf.viewpagerbcaf.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(activity: AppCompatActivity,val listAll:ArrayList<Fragment>):FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
      return listAll.size
    }

    override fun createFragment(position: Int): Fragment {
        return listAll[position]
    }
}