package com.example.miracle.financehelp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.miracle.financehelp.R;
import com.example.miracle.financehelp.adapter.FragmentAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.tableBtn)
    ImageView tableBtn;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.addAccountBtn)
    FloatingActionButton addAccountBtn;
    private FragmentAdapter adapter;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupTabLayout();
        setupFloatingBtn();

    }

    private void setupFloatingBtn() {
        addAccountBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupTabLayout() {
        adapter = new FragmentAdapter(getSupportFragmentManager());
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("账本"));
        tabLayout.addTab(tabLayout.newTab().setText("报表"));
        tabLayout.addTab(tabLayout.newTab().setText("我的"));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @OnClick(R.id.tableBtn)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tableBtn:
                Intent intent = new Intent(MainActivity.this, TableActivity.class);
                startActivity(intent);
                break;
        }
    }

    private long firstTime = 0;

    @Override
    public void onBackPressed() {
        long time = System.currentTimeMillis();
        if (time - firstTime > 2000) {
            Toast.makeText(this, "再按一次推出程序", Toast.LENGTH_SHORT).show();
            firstTime = time;
        } else {
            finish();
            System.exit(0);
        }
    }
}
