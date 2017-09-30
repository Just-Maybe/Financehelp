package com.example.miracle.financehelp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miracle.financehelp.R;
import com.example.miracle.financehelp.entity.User;

import java.util.List;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {
    @Bind(R.id.backImg)
    ImageView backImg;
    @Bind(R.id.userNameEt)
    EditText userNameEt;
    @Bind(R.id.nickNameEt)
    EditText nickNameEt;
    @Bind(R.id.passwordEt)
    EditText passwordEt;
    @Bind(R.id.registerBtn)
    TextView registerBtn;
    @Bind(R.id.content)
    RelativeLayout content;
    private User user;

    private void register(final User user) {
        BmobQuery<User> bmobQuery = new BmobQuery();
        if (Pattern.matches("^[a-zA-Z][a-zA-Z0-9_]{4,15}$", this.userNameEt.getText().toString())) {
            bmobQuery.addWhereEqualTo("userName", userNameEt.getText().toString());
            bmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if (list.size() == 0) {
                        user.setUserName(userNameEt.getText().toString());
                        user.setNickName(nickNameEt.getText().toString());
                        if (Pattern.matches("^[a-zA-Z]\\w{5,17}$", passwordEt.getText().toString())) {
                            user.setPassword(passwordEt.getText().toString());
                            user.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Snackbar.make(RegisterActivity.this.content, "注册失败", -1).show();
                                    }
                                }
                            });
                        } else {
                            Snackbar.make(RegisterActivity.this.content, "密码不符合要求", Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        Snackbar.make(RegisterActivity.this.content, "该用户名已被使用", Snackbar.LENGTH_SHORT).show();
                    }
                }

            });
        } else {
            userNameEt.setHint("字母开头。5-16字，可字母数字下划线");
            Snackbar.make(this.content, "账号不符合要求", Snackbar.LENGTH_SHORT).show();
        }
    }

    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.backImg, R.id.registerBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImg:
                finish();
                break;
            case R.id.registerBtn:
                user = new User();
                register(user);
                break;
        }
    }
}
