package com.example.miracle.financehelp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.miracle.financehelp.App;
import com.example.miracle.financehelp.R;
import com.example.miracle.financehelp.adapter.TableAdapter;
import com.example.miracle.financehelp.entity.Account;
import com.example.miracle.financehelp.entity.AccountDao;
import com.example.miracle.financehelp.entity.AccountDao.Properties;

import org.greenrobot.greendao.Property;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TableActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private TableAdapter adapter;
    private AccountDao dao;
    private boolean isLoadMore = false;
    private int limit = 6;
    private LinearLayoutManager lm;
    private int offset = 0;

    private void setupRecyclerView() {
        List localList = this.dao.queryBuilder().offset(this.offset).limit(this.limit).orderDesc(new Property[]{AccountDao.Properties.Time}).list();
        lm = new LinearLayoutManager(this);
        adapter = new TableAdapter(this, localList);
        offset += this.limit;
        recyclerView.setLayoutManager(this.lm);
        recyclerView.setAdapter(this.adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastVisibleItem;
                if (newState == recyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = lm.findLastCompletelyVisibleItemPosition();
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


    private void setupToolbar() {
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon(R.drawable.back_x32);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                finish();
            }
        });
    }

    public void loadMoreData() {
        List<Account> dataList = dao.queryBuilder().offset(this.offset).limit(this.limit).orderDesc(new Property[]{AccountDao.Properties.Time}).list();
        if (dataList.size() > 0) {
            this.adapter.loadMoreData(dataList);
            this.offset += this.limit;
        } else {
            adapter.setLoadResult("没有更多数据了...");
        }
        isLoadMore = false;
    }

    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_table);
        ButterKnife.bind(this);
        this.dao = App.getDaoSession().getAccountDao();
        setupToolbar();
        setupRecyclerView();
    }
}
