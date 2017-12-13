package org.cchao.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucc on 17/5/2.
 * cc@cchao.org
 */
public class ViewPagerTestActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private List<Fragment> fragments;

    public static void launch(Context context) {
        Intent starter = new Intent(context, ViewPagerTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        viewPager = findViewById(R.id.viewPager);

        fragments = new ArrayList<>();
        fragments.add(ViewPagerFragment.newInstance());
        fragments.add(ViewPagerFragment.newInstance());
        fragments.add(ViewPagerFragment.newInstance());

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }
}
