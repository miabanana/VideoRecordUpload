package com.huayinghuang.videorecordupload;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int TAKE_VIDEO = 1;
    private VideoView mVideoView;
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.record).setOnClickListener(this);
        findViewById(R.id.view).setOnClickListener(this);
        findViewById(R.id.upload).setOnClickListener(this);
        mVideoView = (VideoView)findViewById(R.id.videoView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_VIDEO && resultCode == FragmentActivity.RESULT_OK) {
            mUri = data.getData();
            int msgId = mUri != null? R.string.saved : R.string.record_fail;
            Toast.makeText(this,getString(msgId), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record:
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) == false) {
                    Toast.makeText(this, getString(R.string.camera_fail), Toast.LENGTH_SHORT).show();
                    return;
                }
                takeVieo();
                break;
            case R.id.view:
                viewVideo();
                break;
            case R.id.upload:
                break;
            default:
        }
    }

    private void takeVieo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, TAKE_VIDEO);
        }
    }

    private void viewVideo() {
        if (mVideoView.isPlaying() == false) {
            mVideoView.setVideoURI(mUri);
            mVideoView.start();
        }
    }
}
