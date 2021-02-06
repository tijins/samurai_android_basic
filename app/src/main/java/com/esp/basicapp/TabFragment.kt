package com.esp.basicapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import timber.log.Timber

class TabFragment : Fragment() {
    private lateinit var tabPageAdapter: TabPageAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var pages: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //ページの作成
        pages = arrayListOf("page1", "page2", "page3")

        tabPageAdapter = TabPageAdapter(this, pages)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = tabPageAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = pages[position]
        }.attach()
    }

    class TabPageAdapter(
        fragment: Fragment,
        private val pages: List<String>
    ) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = pages.size

        override fun createFragment(position: Int): Fragment {
            Timber.d("createTab:$position")
            return TabPageFragment.newInstance(pages[position])
        }
    }
}

