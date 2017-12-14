package org.cchao.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by shucc on 17/4/11.
 * cc@cchao.org
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTestActivity.launch(MainActivity.this);
            }
        });
        findViewById(R.id.btn_swipe_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeRefreshTestActivity.launch(MainActivity.this);
            }
        });
        findViewById(R.id.btn_viewpager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPagerTestActivity.launch(MainActivity.this);
            }
        });
    }
}
