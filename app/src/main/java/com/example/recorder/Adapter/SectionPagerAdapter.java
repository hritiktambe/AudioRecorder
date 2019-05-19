package com.example.recorder.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private Map<Integer,String>  mFragmentTags;
    private FragmentManager mFragmentManager;
    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager=fm;
        mFragmentTags = new HashMap<Integer, String>();
    }

    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment){

        mFragmentList.add(fragment);

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if(obj instanceof Fragment) {

            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            mFragmentTags.put(position,tag);

        }
        return obj;
}

    public Fragment getFragment(int position){

        String tag = mFragmentTags.get(position);
        if(tag==null)
            return null;
        return mFragmentManager.findFragmentByTag(tag);
    }

}