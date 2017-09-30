package com.example.miracle.financehelp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.miracle.financehelp.R;
import com.example.miracle.financehelp.entity.User;
import com.example.miracle.financehelp.utils.SPUtils;
import com.example.miracle.financehelp.utils.SizeUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.backImg)
    ImageView backImg;
    @Bind(R.id.userNameEt)
    EditText userNameEt;
    @Bind(R.id.passwordEt)
    EditText passwordEt;
    @Bind(R.id.registerAccountBtn)
    TextView registerAccountBtn;
    @Bind(R.id.loginBtn)
    TextView loginBtn;
    @Bind(R.id.content)
    RelativeLayout content;

    private void Login() {
        BmobQuery<User> bmobQuery = new BmobQuery();
        bmobQuery.addWhereEqualTo("userName", userNameEt.getText().toString());
        bmobQuery.addWhereEqualTo("password", passwordEt.getText().toString());
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (list.size() > 0) {
                    SPUtils.putString("objectId", list.get(0).getObjectId());
                    SPUtils.putString("nickName", list.get(0).getNickName());
                    SPUtils.putString("headImgUrl", list.get(0).getHeadImgUrl());
                    SPUtils.putBoolean("isLogin", true);
                    if (!TextUtils.isEmpty(list.get(0).getDatabaseUrl())) {

                        if (getApplicationContext().getDatabasePath("test.db").exists()) {
                            BmobFile backupFile = new BmobFile("test.db", "", list.get(0).getDatabaseUrl());
                            final File test_backup = new File(getApplicationContext().getDatabasePath("test.db").getParent(), "test_backup.db");
                            backupFile.download(test_backup, new DownloadFileListener() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        try {
                                            fileCopy(test_backup, getApplicationContext().getDatabasePath("test.db"));
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onProgress(Integer integer, long l) {

                                }
                            });

                        }
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(LoginActivity.this.content, "账号或密码错误", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

//        localBmobQuery.findObjects(new FindListener() {
//            public void done(List<User> paramAnonymousList, final BmobException paramAnonymousBmobException) {
//                Log.d("111", "done: " + paramAnonymousList.size());
//                if (paramAnonymousList.size() > 0) {
//                    SPUtils.putString("objectId", (paramAnonymousList.get(0)).getObjectId());
//                    SPUtils.putString("nickName", (paramAnonymousList.get(0)).getNickName());
//                    SPUtils.putString("headImgUrl", (paramAnonymousList.get(0)).getHeadImgUrl());
//                    SPUtils.putBoolean("isLogin", true);
//                    if (LoginActivity.this.getApplicationContext().getDatabasePath("test.db").exists()) {
//                        paramAnonymousList = new BmobFile("test_backup.db", "", ((User) paramAnonymousList.get(0)).getDatabaseUrl());
//                        paramAnonymousBmobException = new File(LoginActivity.this.getApplicationContext().getDatabasePath("test.db").getParent(), "test_backup.db");
//                        paramAnonymousList.download(paramAnonymousBmobException, new DownloadFileListener() {
//                            public void done(String paramAnonymous2String, BmobException paramAnonymous2BmobException) {
//                                try {
//                                    LoginActivity.this.fileCopy(paramAnonymousBmobException, LoginActivity.this.getApplicationContext().getDatabasePath("test.db"));
//                                    Log.d("111", "done: " + paramAnonymous2String);
//                                    return;
//                                } catch (IOException paramAnonymous2BmobException) {
//                                    for (; ; ) {
//                                        paramAnonymous2BmobException.printStackTrace();
//                                    }
//                                }
//                            }
//
//                            public void onProgress(Integer paramAnonymous2Integer, long paramAnonymous2Long) {
//                            }
//                        });
//                    }
//                    paramAnonymousList = new Intent(LoginActivity.this, MainActivity.class);
//                    LoginActivity.this.startActivity(paramAnonymousList);
//                    LoginActivity.this.finish();
//                    return;
//                }
//                Snackbar.make(LoginActivity.this.content, "账号或密码错误", Snackbar.LENGTH_SHORT).show();
//            }
//        });
    }

    private void fileCopy(File dbFile, File backup) throws IOException {
        // TODO Auto-generated method stub
        FileChannel inChannel = new FileInputStream(dbFile).getChannel();
        FileChannel outChannel = new FileOutputStream(backup).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_login);
        SPUtils.getInstance(getApplicationContext());
        ButterKnife.bind(this);
    }


    @OnClick({R.id.backImg, R.id.registerAccountBtn, R.id.loginBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImg:
                finish();
                break;
            case R.id.registerAccountBtn:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.loginBtn:
                Login();
                break;
        }
    }
}
