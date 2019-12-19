package com.appler.riverchiefsystem.utils.version;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;

import java.text.NumberFormat;

/**
 * 下载弹框
 */
public class DownLoadProgressDialog extends AlertDialog {
    /**
     * 声明成员变量
     */
    public OnOkAndCancelListener mOnOkAndCancelListener;
    private ProgressBar mProgress;
    private TextView mProgressNumber;
    private TextView mProgressMessage;
    private Handler mViewUpdateHandler;
    private int mMax;
    private CharSequence mMessage;
    private boolean mHasStarted;
    private int mProgressVal;
    private String TAG = "CommonProgressDialog";
    private String mProgressNumberFormat;
    private NumberFormat mProgressPercentFormat;

    public DownLoadProgressDialog(Context context) {
        super(context);
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_download_progress);
        initFormats();
        mProgress = findViewById(R.id.progress);
        mProgressNumber = findViewById(R.id.progress_number);
        mProgressMessage = findViewById(R.id.progress_message);
        //mProgressImg = (ImageView)findViewById(R.id.progress_img);
        //      LayoutInflater inflater = LayoutInflater.from(getContext());
        mViewUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int progress = mProgress.getProgress();
                int max = mProgress.getMax();
                double dProgress = (double) progress / (double) (1024 * 1024);
                double dMax = (double) max / (double) (1024 * 1024);
                if (mProgressNumberFormat != null) {
                    String format = mProgressNumberFormat;
                    mProgressNumber.setText(String.format(format, dProgress, dMax));
                } else {
                    mProgressNumber.setText("");
                }
                if (mProgressPercentFormat != null) {
                    double percent = (double) progress / (double) max;
                    SpannableString tmp = new SpannableString(mProgressPercentFormat.format(percent));
                    tmp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                            0, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                }
            }
        };
        onProgressChanged();
        if (mMessage != null) {
            setMessage(mMessage);
        }
        if (mMax > 0) {
            setMax(mMax);
        }
        if (mProgressVal > 0) {
//            int iv_height = mProgressImg.getMeasuredHeight();
//            int iv_width = mProgressImg.getMeasuredWidth();
//            int iv_bottom = mProgressImg.getBottom();
//            int progress_left = mProgress.getLeft();
//            int progress_width = mProgress.getMeasuredWidth();
            setProgress(mProgressVal);
//            mProgressImg.layout(progress_left + mProgressVal * (progress_width - iv_width) / mMax, iv_bottom - iv_height, progress_left + mProgressVal * (progress_width - iv_width) / mMax + iv_width, iv_bottom);
        }


    }

    private void initFormats() {
        mProgressNumberFormat = "%1.2fM/%2.2fM";
        mProgressPercentFormat = NumberFormat.getPercentInstance();
        mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    private void onProgressChanged() {
        mViewUpdateHandler.sendEmptyMessage(0);


    }

    public void setProgressStyle(int style) {
        //mProgressStyle = style;
    }

    public int getMax() {
        if (mProgress != null) {
            return mProgress.getMax();
        }
        return mMax;
    }

    public void setMax(int max) {
        if (mProgress != null) {
            mProgress.setMax(max);
            onProgressChanged();
        } else {
            mMax = max;
        }
    }

    public void setIndeterminate(boolean indeterminate) {
        if (mProgress != null) {
            mProgress.setIndeterminate(indeterminate);
        }
        //      else {
        //            mIndeterminate = indeterminate;
        //        }
    }

    public void setProgress(int value) {
        if (mHasStarted) {
            mProgress.setProgress(value);
            onProgressChanged();
        } else {
            mProgressVal = value;
        }
    }

    @Override
    public void setMessage(CharSequence message) {
        //super.setMessage(message);
        if (mProgressMessage != null) {
            mProgressMessage.setText(message);
        } else {
            mMessage = message;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHasStarted = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHasStarted = false;
    }

    /**
     * 暴露方法,设置监听
     */
    public void setOnOkAndCancelListener(OnOkAndCancelListener mOnOkAndCancelListener) {
        this.mOnOkAndCancelListener = mOnOkAndCancelListener;
    }

    /**
     * 暴露接口取消方法,如果需要确定,另行添加
     */
    public interface OnOkAndCancelListener {
        void onCancel(View v);
    }

}
