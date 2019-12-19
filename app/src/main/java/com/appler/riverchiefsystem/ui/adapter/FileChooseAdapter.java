package com.appler.riverchiefsystem.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.bean.FileData;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.ui.activity.PhotoShowActivity;
import com.appler.riverchiefsystem.ui.activity.VideoShowActivity;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileChooseAdapter extends BaseQuickAdapter<FileData, BaseViewHolder> {
    private Context context;
    private List<FileData> data;
    private int flag;
    private OnFileItemLongClick onFileItemLongClick;

    public FileChooseAdapter(Context context, @Nullable List<FileData> data, int flag) {
        super(R.layout.file_choose_layout, data);
        this.context = context;
        this.data = data;
        this.flag = flag;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final FileData item) {
        TextView tv_file_name = helper.getView(R.id.tv_file_name);
        final String filePath = item.getFilePath();
        Log.i(TAG, "convert: " + filePath);
        if (filePath.contains(Global.UPLOAD_FILEPATH)) {
            String filename = item.getFilename();
            Log.i(TAG, "convert filename: " + filename);
            if (filename.contains("/")) {
                String[] split = filename.split("/");
                if (split.length > 1) {
                    filename = split[split.length - 1];
                }
            }
            tv_file_name.setText(filename);
        } else {
            String filename = item.getFilename();
            if (item.getFilePath().contains("/")) {
                String[] split = item.getFilePath().split("/");
                if (split.length > 1) {
                    filename = split[split.length - 1];
                }
            }
            tv_file_name.setText(filename);
        }


        LinearLayout ll_file_list = helper.getView(R.id.ll_file_list);

        ll_file_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filePath.contains(Global.UPLOAD_FILEPATH)) {
                    if (filePath.endsWith("mp4") || filePath.endsWith("MP4") || filePath.endsWith("wav")){
                        Intent intent = new Intent(context, VideoShowActivity.class);
                        intent.putExtra("videoPath",Global.URL + filePath);
                        context.startActivity(intent);
                    }else if (filePath.endsWith("jpg")
                            || filePath.endsWith("png")
                            || filePath.endsWith("jpeg")
                            || filePath.endsWith("JPG")
                            || filePath.endsWith("PNG")
                            || filePath.endsWith("JPEG")) {
                        List<String> list = new ArrayList<>();
                        list.add(Global.URL + filePath);
                        Intent intent = new Intent(context, PhotoShowActivity.class);
                        intent.putStringArrayListExtra("photoList", (ArrayList<String>)list);
                        intent.putExtra("position", 0);
                        context.startActivity(intent);
                    }else {
                        String filename = item.getFilename();
                        if (filename.contains("/")) {
                            String[] split = filename.split("/");
                            if (split.length > 1) {
                                filename = split[split.length - 1];
                            }
                        }
                        String[] split = filename.split(" ");
                        if (split.length > 1) {
                            filename = split[split.length - 1];
                        }
//                        Log.i(TAG, "onClickgetFilePath  file: " + CommonUtils.filePath + File.separator + filename);
                        final File file = new File(CommonUtils.filePath + File.separator + filename);
                        if (!file.exists()) {
//                            Log.i(TAG, "onClickgetFilePath 不存在: " +Global.URL + filePath);
                            CommonUtils.downloadNetFile(Global.URL + filePath);
                        }
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                Log.i(TAG, "onClickgetFilePath 存在: " + file.getAbsolutePath());
                                CommonUtils.openFile(file, context);
                            }
                        }, 1000);
                    }


                } else {
                    CommonUtils.openFile(new File(filePath), context);
                }
            }
        });

        if (flag == 1) {
            ll_file_list.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    onFileItemLongClick.deleteFile(helper.getLayoutPosition());

                    return false;
                }
            });
        }

    }

    public void setOnFileItemLongClick(OnFileItemLongClick onFileItemLongClick) {
        this.onFileItemLongClick = onFileItemLongClick;
    }

    public interface OnFileItemLongClick {
        void deleteFile(int position);
    }
}