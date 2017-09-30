package com.example.miracle.financehelp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.miracle.financehelp.fragment.AccountFragment;
import com.example.miracle.financehelp.fragment.MineFragment;
import com.example.miracle.financehelp.fragment.TableFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    private Fragment[] framgnts = {new AccountFragment(), new TableFragment(), new MineFragment()};
    private String[] titles = {"账本", "报表", "我的"};

    public FragmentAdapter(FragmentManager paramFragmentManager) {
        super(paramFragmentManager);
    }

    public int getCount() {
        return this.framgnts.length;
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return framgnts[0];

            case 1:
                return framgnts[1];

            case 2:
                return framgnts[2];

        }
        return null;
    }

    public CharSequence getPageTitle(int position) {
        return this.titles[position];
    }
}
