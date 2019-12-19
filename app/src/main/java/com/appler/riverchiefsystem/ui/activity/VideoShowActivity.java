package com.appler.riverchiefsystem.ui.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.utils.customView.FullScreenVideoView;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoShowActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();

    @BindView(R.id.iv_toolbar_tv_back)
    ImageView ivToolbarTvBack;
    @BindView(R.id.tv_toolbar_tv_title)
    TextView tvToolbarTvTitle;
    @BindView(R.id.fvv_video)
    FullScreenVideoView vv_video;


    @Override
    protected int bindLayout() {
        return R.layout.activity_video_show;
    }

    @Override
    protected void doBuissness(Context context) {
        tvToolbarTvTitle.setText("视频播放");
        String videoPath = getIntent().getStringExtra("videoPath");
        videoPlay(videoPath);
    }


    @OnClick(R.id.iv_toolbar_tv_back)
    public void onViewClicked() {
        finish();
    }

    private void videoPlay(String videoPath) {
        vv_video.setVideoPath(videoPath);

        /**为控件设置焦点*/
        vv_video.requestFocus();
        /**
         * 为 VideoView 视图设置媒体控制器，设置了之后就会自动由进度条、前进、后退等操作
         */
        vv_video.setMediaController(new MediaController(this));
        vv_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                finish();
            }
        });

        vv_video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                ToastUtils.showShortToast(VideoShowActivity.this,"视频播放错误");
                return false;
            }
        });
        vv_video.start();
    }
}
