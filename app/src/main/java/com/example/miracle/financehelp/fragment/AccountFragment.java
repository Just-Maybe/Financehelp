package com.example.miracle.financehelp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miracle.financehelp.App;
import com.example.miracle.financehelp.R;
import com.example.miracle.financehelp.adapter.AccountAdapter;
import com.example.miracle.financehelp.entity.Account;
import com.example.miracle.financehelp.entity.AccountDao;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountFragment extends Fragment {
    AccountAdapter adapter;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private AccountDao dao;
    private List<Account> dataList;
    private int dataNum = 4;
    private boolean isLoadMore = false;
    LinearLayoutManager lm;
    private int offset = 0;

    private void setupRecyclerView() {
        dataList = new ArrayList();
        dao = App.getDaoSession().getAccountDao();
//        Log.d("111", "setupRecyclerView: " + dao.loadAll().size());
        dataList.addAll(this.dao.queryBuilder().offset(this.offset).limit(this.dataNum).orderDesc(new Property[]{AccountDao.Properties.Time}).list());
        offset += this.dataNum;
        adapter = new AccountAdapter(getActivity(), dataList);
        lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == recyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = lm.findLastVisibleItemPosition();
                    if (lm.getItemCount() == 1) {
                        return;
                    }
                    if (lastVisibleItem + 1 == lm.getItemCount() && !isLoadMore) {
                        loadMoreData();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void loadMoreData() {
        List localList = this.dao.queryBuilder().offset(this.offset).limit(this.dataNum).orderDesc(new Property[]{AccountDao.Properties.Time}).list();
        if (localList.size() > 0) {
            adapter.loadMoreData(localList);
            offset += dataNum;
        } else {
            adapter.setLoadResult("没有更多数据了...");
        }
        isLoadMore = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    public void onResume() {
        super.onResume();
        offset = 0;
        setupRecyclerView();
    }
}
