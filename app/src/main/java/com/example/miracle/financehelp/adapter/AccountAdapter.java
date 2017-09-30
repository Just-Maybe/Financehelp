package com.example.miracle.financehelp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.miracle.financehelp.App;
import com.example.miracle.financehelp.R;
import com.example.miracle.financehelp.entity.Account;
import com.example.miracle.financehelp.entity.AccountDao;
import com.example.miracle.financehelp.entity.AccountDao.Properties;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountAdapter extends RecyclerView.Adapter {
    private static final int FOOTERVIEW = 321;
    private static final int HEADVIEW = 367;
    private static final int INCOMEITEMVIEW = 327;
    private static final int OUTCOMEITEMVIEW = 337;
    private float incomeTotal;
    private String loadResult = "上拉加载更多";
    private Context mContext;
    private List<Account> mDataList;
    private final int month;
    private String monthTime;
    private float outcomeToal;
    private String todayTime;

    public AccountAdapter(Context paramContext, List<Account> paramList) {
        this.mContext = paramContext;
        this.mDataList = paramList;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        monthTime = (year + "-" + month);
        todayTime = (year + "-" + month + "-" + day);
        AccountDao dao = App.getDaoSession().getAccountDao();
        Date date1 = new Date(year, month, 0);
        Date date2 = new Date(year, month, 31);
        List<Account> monthIncome = dao.queryBuilder().where(AccountDao.Properties.Time.between(date1, date2), Properties.Category.eq(1)).list();
        List<Account> monthOutcome = dao.queryBuilder().where(AccountDao.Properties.Time.between(date1, date2), Properties.Category.eq(2)).list();
        for (int i = 0; i < monthIncome.size(); i++) {
            incomeTotal += monthIncome.get(i).getTotal();
        }
        for (int i = 0; i < monthOutcome.size(); i++) {
            outcomeToal += monthOutcome.get(i).getTotal();
        }
    }

    public int getItemCount() {
        return this.mDataList.size() + 2;
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADVIEW;
        } else if (position > 0 && (position < getItemCount() - 1) && (mDataList.get(position - 1)).getCategory() == 1) {
            return INCOMEITEMVIEW;
        } else if ((position > 0) && (position < getItemCount() - 1) && (mDataList.get(position - 1)).getCategory() == 2) {
            return OUTCOMEITEMVIEW;
        } else if (position == getItemCount() - 1) {
            return FOOTERVIEW;
        }
        return 0;
    }

    public String getLoadResult() {
        return this.loadResult;
    }

    public void loadMoreData(List<Account> paramList) {
        this.mDataList.addAll(paramList);
        notifyDataSetChanged();
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if ((holder instanceof headViewHolder)) {
            ((headViewHolder) holder).budget.setText("月预算" + String.valueOf(incomeTotal - outcomeToal));
            ((headViewHolder) holder).monthlyIncome.setText(month + 1 + "月份收入 " + this.incomeTotal);
            ((headViewHolder) holder).monthlyOutcome.setText(month + 1 + "月份支出 " + this.outcomeToal);
        } else if ((holder instanceof incomeViewHolder)) {
            int imageResource = mContext.getResources().getIdentifier((mDataList.get(position - 1)).getIncomeImage(), null, mContext.getPackageName());
            Glide.with(this.mContext).load(imageResource).into(((incomeViewHolder) holder).typeImage);
            ((incomeViewHolder) holder).contentTv.setText((mDataList.get(position - 1)).getIncometypeName() + ":￥" + (mDataList.get(position - 1)).getTotal());
            if ((position >= 2) && ((mDataList.get(position - 1)).getTime().getDate() == (mDataList.get(position - 2)).getTime().getDate())) {
                ((incomeViewHolder) holder).today.setVisibility(View.GONE);
            } else {
                ((incomeViewHolder) holder).today.setVisibility(View.VISIBLE);
                Date date = (this.mDataList.get(position - 1)).getTime();
                if ((date.getYear() + "-" + date.getMonth() + "-" + date.getDate()).equals(todayTime)) {
                    ((incomeViewHolder) holder).today.setText("今天");
                } else {
                    String dateString = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
                    ((incomeViewHolder) holder).today.setText(dateString);
                }
            }

        } else if ((holder instanceof outcomeViewHolder)) {
            int imageResource = this.mContext.getResources().getIdentifier((mDataList.get(position - 1)).getOutcomeImage(), null, mContext.getPackageName());
            Glide.with(this.mContext).load(imageResource).into(((outcomeViewHolder) holder).typeImage);
            ((outcomeViewHolder) holder).contentTv.setText((mDataList.get(position - 1)).getOutcomeTypeName() + ":￥" + (mDataList.get(position - 1)).getTotal());
            if ((position >= 2) && ((mDataList.get(position - 1)).getTime().getDate() == (mDataList.get(position - 2)).getTime().getDate())) {
                ((outcomeViewHolder) holder).today.setVisibility(View.GONE);
            } else {
                ((outcomeViewHolder) holder).today.setVisibility(View.VISIBLE);
            }
            Date date = (this.mDataList.get(position - 1)).getTime();
            if ((date.getYear() + "-" + date.getMonth() + "-" + date.getDate()).equals(this.todayTime)) {
                ((outcomeViewHolder) holder).today.setText("今天");
            } else {
                String dateString = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
                ((outcomeViewHolder) holder).today.setText(dateString);
            }
        } else if (holder instanceof footerViewHolder) {
            if (mDataList.size() < 4) {
                ((footerViewHolder) holder).footerTv.setText("记录生活的点点滴滴...");
            } else {
                ((footerViewHolder) holder).footerTv.setText(loadResult);
            }
        }
    }


    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View headView = LayoutInflater.from(this.mContext).inflate(R.layout.account_head_layout, parent, false);
        View incomeView = LayoutInflater.from(this.mContext).inflate(R.layout.item_income_layout, parent, false);
        View outcomeView = LayoutInflater.from(this.mContext).inflate(R.layout.item_outcome_layout, parent, false);
        View footView = LayoutInflater.from(this.mContext).inflate(R.layout.account_footer_layout, parent, false);
        if (viewType == HEADVIEW) {
            return new headViewHolder(headView);
        }
        if (viewType == INCOMEITEMVIEW) {
            return new incomeViewHolder(incomeView);
        }
        if (viewType == OUTCOMEITEMVIEW) {
            return new outcomeViewHolder(outcomeView);
        }
        if (viewType == FOOTERVIEW) {
            return new footerViewHolder(footView);
        }
        return null;
    }

    public void setLoadResult(String paramString) {
        this.loadResult = paramString;
        notifyDataSetChanged();
    }

    static class footerViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.footerTv)
        TextView footerTv;

        public footerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class headViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.budgetTv)
        TextView budget;
        @Bind(R.id.monthlyIncomeTv)
        TextView monthlyIncome;
        @Bind(R.id.monthlyOutcomeTv)
        TextView monthlyOutcome;

        public headViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class incomeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.contentTv)
        TextView contentTv;
        @Bind(R.id.dateTv)
        TextView today;
        @Bind(R.id.typeImage)
        ImageView typeImage;

        public incomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class outcomeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.contentTv)
        TextView contentTv;
        @Bind(R.id.dateTv)
        TextView today;
        @Bind(R.id.typeImage)
        ImageView typeImage;

        public outcomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
