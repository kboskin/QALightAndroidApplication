package com.example.hp.qalightandroidapp.fragments.materialsandtests;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.HomeTaskFragment;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.MaterialsFragment;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.tests.TestsFragment;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class FixturesTabsFragment extends Fragment {

    private HomeTaskFragment homeTaskFragment;
    private MaterialsFragment materialsFragment;
    private TestsFragment testsFragment;
    private Date date;

    public FixturesTabsFragment(){}

    public FixturesTabsFragment(Date date) {
        this.date = date;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fixtures_tabs, container, false);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);
        return view;

    }


    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        // fragments creation, only once, so
        // we will not have to recreate
        if(date!=null){
            homeTaskFragment = new HomeTaskFragment(date);
            materialsFragment = new MaterialsFragment();
            testsFragment = new TestsFragment();
        } else {
            homeTaskFragment = new HomeTaskFragment();
            materialsFragment = new MaterialsFragment();
            testsFragment = new TestsFragment();
        }


        //creating adapter and fragments to adapter
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(homeTaskFragment, getString(R.string.home_task));
        adapter.addFragment(materialsFragment, getString(R.string.materials));
        adapter.addFragment(testsFragment, getString(R.string.tests));
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}