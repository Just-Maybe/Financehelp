package com.example.miracle.financehelp.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.miracle.financehelp.R;
import com.example.miracle.financehelp.fragment.IncomeFragment;
import com.example.miracle.financehelp.fragment.OutcomeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.contentLayout)
    FrameLayout contentLayout;
    @Bind(R.id.insertAccountBtn)
    FloatingActionButton insertAccountBtn;
    private FragmentManager fm;

    private void setupContentLayout() {
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.contentLayout, new OutcomeFragment()).commit();
    }

    private void setupToolBar() {
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                AccountActivity.this.finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem MenuItem) {
                switch (MenuItem.getItemId()) {
                    case R.id.income_menu:
                        fm.beginTransaction().setCustomAnimations(R.anim.fragment_slide_enter, R.anim.fragment_slide_exit).replace(R.id.contentLayout, new IncomeFragment()).commit();
                        toolbar.setTitle("收入");
                        break;
                    case R.id.outcome_menu:
                        fm.beginTransaction().setCustomAnimations(R.anim.fragment_slide_enter, R.anim.fragment_slide_exit).replace(R.id.contentLayout, new OutcomeFragment()).commit();
                        toolbar.setTitle("支出");
                        break;
                }
                return false;
            }
        });
    }


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        setupToolBar();
        setupContentLayout();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }
}
