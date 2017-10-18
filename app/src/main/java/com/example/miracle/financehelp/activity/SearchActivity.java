package com.example.miracle.financehelp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.miracle.financehelp.App;
import com.example.miracle.financehelp.R;
import com.example.miracle.financehelp.adapter.TableAdapter;
import com.example.miracle.financehelp.entity.Account;
import com.example.miracle.financehelp.entity.AccountDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Miracle on 2017/10/17 0017.
 */

public class SearchActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.searchView)
    SearchView mSearchView;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private AccountDao dao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupToolbar();
        setupSearchView();
        dao = App.getDaoSession().getAccountDao();
    }

    private void setupToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });
    }

    private void setupRecyclerView(List<Account> mList) {
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        TableAdapter adapter = new TableAdapter(this, mList);
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchView() {
        SearchView.SearchAutoComplete mSearchAutoComplete = mSearchView.findViewById(R.id.search_src_text);
        mSearchAutoComplete.setTextColor(getResources().getColor(R.color.colorWhite));
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setFocusable(true);
        mSearchView.setIconified(false);
        mSearchView.requestFocusFromTouch();
        mSearchAutoComplete.setHint("输入搜索内容");
        mSearchAutoComplete.setHintTextColor(getResources().getColor(R.color.colorWhite));
        mSearchAutoComplete.setTextSize(17);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //%你想匹配的%
            @Override
            public boolean onQueryTextChange(String newText) {
                QueryBuilder<Account> qb = dao.queryBuilder();
                List<Account> mList = qb.where(qb.or(AccountDao.Properties.Remark.like("%" + newText + "%"),
                        AccountDao.Properties.Total.like("%" + newText + "%"),
//                        AccountDao.Properties.Time.like("%"+newText+"%"),
                        AccountDao.Properties.IncometypeName.like("%" + newText + "%"),
                        AccountDao.Properties.OutcomeTypeName.like("%" + newText + "%"))).list();
                Log.d("111", "onQueryTextChange: " + mList.size());
                setupRecyclerView(mList);
                return true;
            }
        });
    }
}
