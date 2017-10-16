package com.example.miracle.financehelp.adapter;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.miracle.financehelp.App;
import com.example.miracle.financehelp.R;
import com.example.miracle.financehelp.activity.AccountActivity;
import com.example.miracle.financehelp.activity.TableActivity;
import com.example.miracle.financehelp.entity.Account;
import com.example.miracle.financehelp.entity.AccountDao;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TableAdapter extends RecyclerView.Adapter {
    private static final int FOOTERVIEW = 617;
    private static final int ITEMVIEW = 453;
    private String LoadResult;
    private AccountDao dao;
    private Context mContext;
    private List<Account> mList;

    public TableAdapter(Context paramContext, List<Account> paramList) {
        this.mContext = paramContext;
        this.mList = paramList;
        this.dao = App.getDaoSession().getAccountDao();
    }

    private void deleteItem(int position) {
        this.dao.deleteByKey((mList.get(position)).getId());
        this.mList.remove(position);
        notifyItemRemoved(position);
    }

    public int getItemCount() {
        return this.mList.size() + 1;
    }

    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return FOOTERVIEW;
        }
        return ITEMVIEW;
    }

    public String getLoadResult() {
        return this.LoadResult;
    }

    public void loadMoreData(List<Account> paramList) {
        this.mList.addAll(paramList);
        notifyDataSetChanged();
    }

    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if ((holder instanceof itemHolder)) {
            Date date = (mList.get(position)).getTime();
            String dateStr = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
            ((itemHolder) holder).timeTv.setText(dateStr);
            int imageResource = 0;
            if ((mList.get(position)).getCategory() == 1) {
                ((itemHolder) holder).categoryTv.setText("收入");
                ((itemHolder) holder).paymentTv.setText((mList.get(position)).getIncometypeName());
                imageResource = mContext.getResources().getIdentifier((mList.get(position)).getIncomeImage(), null, mContext.getPackageName());
            } else if ((mList.get(position)).getCategory() == 2) {
                ((itemHolder) holder).categoryTv.setText("支出");
                ((itemHolder) holder).paymentTv.setText((mList.get(position)).getOutcomeTypeName());
                imageResource = mContext.getResources().getIdentifier((mList.get(position)).getOutcomeImage(), null, mContext.getPackageName());
            }
            ((itemHolder) holder).remarkTv.setText(mList.get(position).getRemark());
            Glide.with(mContext).load(imageResource).into(((itemHolder) holder).paymentImg);
            ((itemHolder) holder).totalTv.setText("￥ " + (mList.get(position)).getTotal());
            ((itemHolder) holder).deleteBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(holder.itemView, "translationX", new float[]{0.0F, -holder.itemView.getMeasuredWidth()});
                    animator.setDuration(1000);
                    animator.start();
                    animator.addListener(new AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            deleteItem(position);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                }
            });
            ((itemHolder) holder).updateBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    long id = mList.get(position).getId();
                    Log.d("111", "onClick: "+id);
                    Intent intent = new Intent(mContext, AccountActivity.class);
                    intent.putExtra("AccountId", id);
                    intent.putExtra("flag",mList.get(position).getCategory());
                    mContext.startActivity(intent);
                }
            });
        }

    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.mContext).inflate(R.layout.item_table_layout, parent, false);
        View footView = LayoutInflater.from(this.mContext).inflate(R.layout.table_footer_layout, parent, false);
        if (viewType == ITEMVIEW) {
            return new itemHolder(itemView);
        } else if (viewType == FOOTERVIEW) {
            return new footerHolder(footView);
        }
        return null;
    }

    public void setLoadResult(String paramString) {
        this.LoadResult = paramString;
    }

    static class footerHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.footerTv)
        TextView footerTv;

        public footerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class itemHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.categoryTv)
        TextView categoryTv;
        @Bind(R.id.deleteBtn)
        TextView deleteBtn;
        @Bind(R.id.paymentImg)
        ImageView paymentImg;
        @Bind(R.id.paymentTv)
        TextView paymentTv;
        @Bind(R.id.remarkTv)
        TextView remarkTv;
        @Bind(R.id.timeTv)
        TextView timeTv;
        @Bind(R.id.totalTv)
        TextView totalTv;
        @Bind(R.id.updateBtn)
        TextView updateBtn;

        public itemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
