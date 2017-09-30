package com.example.miracle.financehelp.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.miracle.financehelp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HeadImgDialog extends Dialog {
    OnCLickListener listener;

    @Bind(R.id.cameraPhoto)
    TextView cameraPhoto;
    @Bind(R.id.galleryPhoto)
    TextView galleryPhoto;

    public HeadImgDialog(@NonNull Context paramContext) {
        super(paramContext);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.dialog_head);
        ButterKnife.bind(this, this);
    }


    @OnClick({R.id.cameraPhoto, R.id.galleryPhoto})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cameraPhoto:
                listener.onCameraPhotoClick(view);
                break;
            case R.id.galleryPhoto:
                listener.onGalleryPhotoClick(view);
                break;
        }
    }

    public void setOnItemClickListner(OnCLickListener listen) {
        this.listener = listen;
    }

    public interface OnCLickListener {
        void onCameraPhotoClick(View view);

        void onGalleryPhotoClick(View view);
    }
}
