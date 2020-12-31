package com.faceDemo.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.faceDemo.R;
import com.faceDemo.utils.PermissionUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static String TAG = "MainActivity";
    private ImageView mEffectVideo;
    private boolean isCameraFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        findViewById(R.id.fontDetect).setOnClickListener(this);
        findViewById(R.id.backDetect).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fontDetect:
                isCameraFlag = true;
                startVideoWithFaceDetected(true);
                break;
            case R.id.backDetect:
                isCameraFlag = false;
                startVideoWithFaceDetected(false);
                break;
        }
    }

    private void startVideoWithFaceDetected(boolean flag) {
        PermissionUtils.checkPermission(this, new Runnable() {
            @Override
            public void run() {
                jumpToCameraActivity(flag);
            }
        });
    }

    public void jumpToCameraActivity(boolean flag)
    {
        Intent intent = new Intent(MainActivity.this, NewClassifierActivity.class);
        intent.putExtra(NewClassifierActivity.CAMERA_FONT_FLAG,flag);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                jumpToCameraActivity(isCameraFlag);
            } else {
                startVideoWithFaceDetected(isCameraFlag);
            }
        }
    }
}
