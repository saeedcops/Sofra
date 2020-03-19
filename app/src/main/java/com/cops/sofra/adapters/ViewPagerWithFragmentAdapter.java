package com.cops.sofra.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerWithFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mFragmentsTitle = new ArrayList<>();


    public ViewPagerWithFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void addPager(Fragment fragments, String fragmentTitle) {
        this.mFragments.add(fragments);
        this.mFragmentsTitle.add(fragmentTitle);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentsTitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentsTitle.get(position);
    }
}
