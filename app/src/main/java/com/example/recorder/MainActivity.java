package com.example.recorder;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMuxer;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recorder.Adapter.SectionPagerAdapter;
import com.example.recorder.fragments.Tab1Fragment;
import com.example.recorder.fragments.Tab2Fragment;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {



    private SectionPagerAdapter mSectionPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        setUpViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_child_care);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_directions);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                Fragment fragment = ((SectionPagerAdapter)mViewPager.getAdapter()).getFragment(i);

                if(i==1 && fragment!=null){
                    fragment.onResume();
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment());
        adapter.addFragment(new Tab2Fragment());
        viewPager.setAdapter(adapter);

    }

}
