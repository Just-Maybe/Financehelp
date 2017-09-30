package com.example.miracle.financehelp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.miracle.financehelp.R;
import com.example.miracle.financehelp.entity.User;
import com.example.miracle.financehelp.utils.SPUtils;
import com.example.miracle.financehelp.utils.SizeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UpdatePasswordActivity
        extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.newPasswordEt)
    EditText newPasswordEt;
    @Bind(R.id.confirmPasswordEt)
    EditText confirmPasswordEt;
    @Bind(R.id.updateBtn)
    TextView updateBtn;
    @Bind(R.id.content)
    LinearLayout content;

    private void setupToolbar() {
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon(R.drawable.back_x32);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                UpdatePasswordActivity.this.finish();
            }
        });
    }

    private void updatePassword() {
        if (this.newPasswordEt.getText().toString().equals(this.confirmPasswordEt.getText().toString())) {
            User localUser = new User();
            localUser.setPassword(this.confirmPasswordEt.getText().toString());
            localUser.update(SPUtils.getString("objectId"), new UpdateListener() {
                public void done(BmobException paramAnonymousBmobException) {
                    if (paramAnonymousBmobException == null) {
                        Snackbar.make(UpdatePasswordActivity.this.content, "修改成功", Snackbar.LENGTH_SHORT).show();
                        UpdatePasswordActivity.this.finish();
                        return;
                    }
                    Snackbar.make(UpdatePasswordActivity.this.content, "修改失败", Snackbar.LENGTH_SHORT).show();
                }
            });
            return;
        }
        Snackbar.make(this.content, "密码不一致", Snackbar.LENGTH_SHORT).show();
    }

    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_update_password);
        ButterKnife.bind(this);
        SPUtils.getInstance(getApplicationContext());
        setupToolbar();
    }


    @OnClick(R.id.updateBtn)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.updateBtn:
                updatePassword();
                break;
        }
    }
}
