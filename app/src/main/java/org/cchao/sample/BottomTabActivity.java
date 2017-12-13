package org.cchao.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucc on 17/6/26.
 * cc@cchao.org
 */
public class BottomTabActivity extends AppCompatActivity {

    private TextView textOne;
    private TextView textTwo;
    private TextView textThree;

    private TestFragmentOne testFragmentOne;
    private TestFragmentTwo testFragmentTwo;
    private TestFragmentOne testFragmentThree;

    private List<TextView> textViews;
    private SparseArray<Fragment> fragments;

    private int nowSelect = 0;

    public static void launch(Context context) {
        Intent starter = new Intent(context, BottomTabActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);

        textOne = findViewById(R.id.text_one);
        textTwo = findViewById(R.id.text_two);
        textThree = findViewById(R.id.text_three);

        textViews = new ArrayList<>();
        fragments = new SparseArray<>();

        textViews.add(textOne);
        textViews.add(textTwo);
        textViews.add(textThree);
        for (int i = 0; i < textViews.size(); i++) {
            textViews.get(i).setOnClickListener(new OnBottomTabClickListener(i));
        }

        changeTab(0);
    }

    private void changeTab(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (null != fragments.get(nowSelect)) {
            fragmentTransaction.hide(fragments.get(nowSelect));
        }
        if (null == fragments.get(position)) {
            switch (position) {
                case 0:
                    testFragmentOne = new TestFragmentOne();
                    fragments.put(0, testFragmentOne);
                    break;
                case 1:
                    testFragmentTwo = new TestFragmentTwo();
                    fragments.put(1, testFragmentTwo);
                    break;
                case 2:
                    testFragmentThree = new TestFragmentOne();
                    fragments.put(2, testFragmentThree);
                    break;
                default:
                    break;
            }
            fragmentTransaction.add(R.id.fl_fragment, fragments.get(position));
        } else {
            fragmentTransaction.show(fragments.get(position));
        }
        fragmentTransaction.commit();
        textViews.get(nowSelect).setBackgroundDrawable(null);
        textViews.get(position).setBackgroundColor(getResources().getColor(R.color.colorAccent));
        nowSelect = position;
    }

    private class OnBottomTabClickListener implements View.OnClickListener {

        private int position;

        public OnBottomTabClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (position == nowSelect) {
                return;
            }
            changeTab(position);
        }
    }
}
