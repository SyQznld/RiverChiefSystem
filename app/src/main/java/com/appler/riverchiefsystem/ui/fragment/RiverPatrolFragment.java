package com.appler.riverchiefsystem.ui.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.amap.api.services.core.LatLonPoint;
import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.bean.EventBusData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.ui.activity.AddPatrolActivity;
import com.appler.riverchiefsystem.ui.activity.AddProblemActivity;
import com.appler.riverchiefsystem.ui.activity.ProblemDetailActivity;
import com.appler.riverchiefsystem.utils.AMapUtil;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.CoordinateUtil;
import com.appler.riverchiefsystem.utils.CountingRequestBody;
import com.appler.riverchiefsystem.utils.MainHandler;
import com.appler.riverchiefsystem.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * 河流巡查页面
 */
public class RiverPatrolFragment extends Fragment implements LocationSource, AMapLocationListener {
    private String TAG = getClass().getSimpleName();
    @BindView(R.id.iv_wxt)
    ImageView ivWxt;
    @BindView(R.id.tv_wxt)
    TextView tvWxt;
    @BindView(R.id.ll_wxt)
    LinearLayout llWxt;
    @BindView(R.id.iv_yjdt)
    ImageView ivYjdt;
    @BindView(R.id.tv_yjdt)
    TextView tvYjdt;
    @BindView(R.id.ll_yjdt)
    LinearLayout llYjdt;
    @BindView(R.id.iv_dhdt)
    ImageView ivDhdt;
    @BindView(R.id.tv_dhdt)
    TextView tvDhdt;
    @BindView(R.id.ll_dhdt)
    LinearLayout llDhdt;
    @BindView(R.id.dl_riverPatrol)
    DrawerLayout dlRiverPatrol;
    Unbinder unbinder;

    @BindView(R.id.tv_riverPatrol_title)
    TextView tvRiverPatrolTitle;
    @BindView(R.id.iv_riverPatrol_layer)
    ImageView ivLayer;
    @BindView(R.id.gdMapView)
    MapView mapView;
    @BindView(R.id.tv_riverPatrol_gps)
    TextView tvRiverPatrolGps;
    @BindView(R.id.riverPatrol11)
    Button riverPatrol11;
    @BindView(R.id.btn_riverPatrol_begin)
    Button btnRiverPatrolBegin;
    @BindView(R.id.btn_riverPatrol_end)
    Button btnRiverPatrolEnd;
    @BindView(R.id.tv_riverPatrol_takephoto)
    TextView tvRiverPatrolTakephoto;
    @BindView(R.id.tv_riverPatrol_report)
    TextView tvRiverPatrolReport;
    @BindView(R.id.rl_riverPatrol)
    RelativeLayout rlRiverPatrol;
    @BindView(R.id.ib_riverPatrol_nowLocation)
    ImageButton ibRiverPatrolNowLocation;
    @BindView(R.id.ib_riverPatrol_huifu)
    ImageButton ibRiverPatrolNowHuifu;
    @BindView(R.id.ib_riverPatrol_zoomIn)
    ImageButton ibRiverPatrolZoomIn;
    @BindView(R.id.ib_riverPatrol_zoomOut)
    ImageButton ibRiverPatrolZoomOut;

    private AMap aMap;          //地图对象
    //定位需要的声明,初始化的配置
    private AMapLocationClient mLocationClient = null;          //发起定位
    private AMapLocationClientOption mLocationOption = null;    //参数设置
    private OnLocationChangedListener mListener = null;         //监听器
    private View mapLayout;
    private double longitude, latitude;
    //地图基本操作相关
    private CameraPosition cameraPosition;
    private float mapZoom;
    private LatLng mapTarget;
    //以前的定位点
    private LatLng oldLatLng;


    //巡查相关
    private int patrolFlag = 0;
    private List<String> patrolList = new ArrayList<>();
    private String patrolBeginTime = "";  //巡查开始时间
    private String locationFlag = "定位";  //定位 或者 绘制巡查记录
    private String partolId = "";

    //照片相关
    private String defaultPhotoAddress;
    private String photoFolderAddress;
    private List<String> listFileNames;
    private int CAMERA_REQUEST_CODE = 111;
    private int VIDEO_REQUEST_CODE = 112;
    private File file;
    private String fileName;
    private AlertDialog show;
    private AMapLocation aMapLocation = null;
    private Marker marker, startMarker, endMarker;
    private Polyline polyline;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mapLayout == null) {
            mapLayout = inflater.inflate(R.layout.fragment_river_patrol, null);
            rlRiverPatrol = mapLayout.findViewById(R.id.rl_riverPatrol);
            mapView = (MapView) mapLayout.findViewById(R.id.gdMapView);
            mapView.onCreate(savedInstanceState);
            if (aMap == null) {
                //获取地图对象
                aMap = mapView.getMap();
            }

            //地图相关设置操作
            initMapOperation();
            //判断巡查布局根据用户角色显示 初始化图片视频
            initPatrolAndPhotos();

        } else {
            if (mapLayout.getParent() != null) {
                ((ViewGroup) mapLayout.getParent()).removeView(mapLayout);
            }
        }
        unbinder = ButterKnife.bind(this, mapLayout);
        return mapLayout;
    }


    @SuppressLint("SimpleDateFormat")
    @OnClick({R.id.iv_riverPatrol_layer,
            R.id.ll_wxt, R.id.ll_yjdt, R.id.ll_dhdt,
            R.id.btn_riverPatrol_begin, R.id.btn_riverPatrol_end, R.id.tv_riverPatrol_takephoto, R.id.tv_riverPatrol_report,
            R.id.ib_riverPatrol_nowLocation, R.id.ib_riverPatrol_zoomIn, R.id.ib_riverPatrol_zoomOut, R.id.ib_riverPatrol_huifu})
    public void onViewClicked(View view) {
        cameraPosition = aMap.getCameraPosition();
        mapZoom = cameraPosition.zoom;
        mapTarget = cameraPosition.target;
        switch (view.getId()) {
            case R.id.iv_riverPatrol_layer:   //图层
                dlRiverPatrol.openDrawer(GravityCompat.END);
                break;
            case R.id.ll_wxt:   //卫星图
                showLayer("卫星图", ivWxt, tvWxt, AMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.ll_yjdt:   //夜景地图
                showLayer("夜景地图", ivYjdt, tvYjdt, AMap.MAP_TYPE_NIGHT);
                break;
            case R.id.ll_dhdt:   //导航地图
                showLayer("导航地图", ivDhdt, tvDhdt, AMap.MAP_TYPE_NAVI);
                break;
            case R.id.btn_riverPatrol_begin:   //开始巡查
                beginPatrol();
                break;
            case R.id.btn_riverPatrol_end:     //结束巡查
                stopPatrol();
                break;
            case R.id.tv_riverPatrol_takephoto:   //拍摄图片、视频
                if (listFileNames.size() >= 9) {
                    ToastUtils.showShortToast(getActivity(), "最多保存九张图片");
                } else {
                    takePhotoOrVideo();
                }
                break;
            case R.id.tv_riverPatrol_report:      //问题上报
                Intent reportIntent = new Intent(getActivity(), AddProblemActivity.class);
                reportIntent.putExtra("flag", 1);
                reportIntent.putExtra("patrol_id", partolId);
                startActivity(reportIntent);
                break;
            case R.id.ib_riverPatrol_zoomIn:   //放大
                scaleLargeMap(mapTarget, ++mapZoom);
                break;
            case R.id.ib_riverPatrol_zoomOut:  //缩小
                scaleLargeMap(mapTarget, --mapZoom);
                break;
            case R.id.ib_riverPatrol_nowLocation:  //重新定位
                if (null != aMap && 0.0 != latitude && 0.0 != longitude) {
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), mapZoom));
                }
                break;
            case R.id.ib_riverPatrol_huifu:  //重置地图  正常地图模式
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);

                break;
        }
    }


    /**
     * 判断巡查布局根据用户角色显示 初始化图片视频
     */
    private void initPatrolAndPhotos() {
        //判断巡查相关是否显示
        String rolename = AdminDao.getAdmin().getRolename();
        if (!rolename.equals(Global.ROLENAME_CUN_HEZHANG)
                && !rolename.equals(Global.ROLENAME_XIANG_HEZHANG)
                && !rolename.equals(Global.ROLENAME_ZHEN_HEZHANG)) {   //市、区、县河长不显示巡查
            rlRiverPatrol.setVisibility(View.GONE);
        } else {   //村、乡、镇河长巡查
            rlRiverPatrol.setVisibility(View.VISIBLE);
        }


        //保存图片、视频相关
        listFileNames = new ArrayList<>();
        defaultPhotoAddress = CommonUtils.getSDPath() + File.separator + ".jpg";
        photoFolderAddress = CommonUtils.getSDPath() + File.separator + "河流巡查系统" + File.separator + "巡查记录";
//        file = videoRename();
    }


    //开始巡查
    private void beginPatrol() {
        patrolList.clear();
        listFileNames.clear();
        patrolBeginTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        patrolFlag = 1;
        tvRiverPatrolTitle.setText("巡查中");
        btnRiverPatrolBegin.setVisibility(View.GONE);
        btnRiverPatrolEnd.setVisibility(View.VISIBLE);

//        插入一条空记录
        insertPatrol();
        tvRiverPatrolTakephoto.setVisibility(View.VISIBLE);
        tvRiverPatrolReport.setVisibility(View.VISIBLE);
    }


    //结束巡查
    private void stopPatrol() {
        Intent intent = new Intent(getActivity(), AddPatrolActivity.class);
        intent.putStringArrayListExtra("patrolPhotoList", (ArrayList<String>) listFileNames);
        intent.putExtra("patrolRecordGps", TextUtils.join(",", patrolList));
        intent.putExtra("patrolRecordBegintime", patrolBeginTime);
        intent.putExtra("partolId", partolId);
        startActivityForResult(intent, Global.PATROL_PHOTO_CODE);
    }


    /**
     * 插入一条空记录
     */
    private void insertPatrol() {
        final OkHttpClient mOkHttpClient = new OkHttpClient();
        final MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("flag", FlagConstant.FLAG_PATROL_ADDOREDIT);
        requestBody.addFormDataPart("sys_staff_id", String.valueOf(AdminDao.getAdminID()));
        requestBody.addFormDataPart("patrol_record_begintime", patrolBeginTime);
        requestBody.addFormDataPart("patrol_record_endtime", "");
        requestBody.addFormDataPart("patrol_record_gps", "");
        requestBody.addFormDataPart("sys_river_id", "");
        requestBody.addFormDataPart("sys_patrol_record_id", "");
        requestBody.addFormDataPart("riveraddress", "");
        List<String> uploadList = new ArrayList<>();
        for (int i = 0; i < uploadList.size(); i++) {
            String s1 = uploadList.get(i);
            if (!"".equals(s1)) {
                File file = new File(s1);
                RequestBody body = RequestBody.create(MediaType.parse("text/x-markdown"), file);
                CountingRequestBody countingRequestBody = new CountingRequestBody(body, new CountingRequestBody.Listener() {
                    @Override
                    public void onRequestProgress(long byteWritted, long contentLength) {
                        //打印进度
                    }
                });
                String filename = file.getName();
                requestBody.addFormDataPart("pic_path", filename, countingRequestBody);
            }
        }
        Request request = new Request.Builder()
                .url(Global.Upload_Url)
                .post(requestBody.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = e.getMessage();
                e.printStackTrace();
                MainHandler.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast(getActivity(), message);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                MainHandler.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        if (string.contains("Success")) {
                            if (string.contains(",")) {
                                String[] split = string.split(",");
                                partolId = split[1].trim();
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 初始化地图操作
     */
    private void initMapOperation() {
        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        //设置定位监听
        aMap.setLocationSource(this);
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(false);
        settings.setZoomControlsEnabled(false);
        settings.setLogoBottomMargin(-50);//隐藏logo
        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);

//        aMap.setTrafficEnabled(true);//显示实时路况图层，aMap是地图控制器对象。

        //定位的小图标
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        aMap.setMyLocationStyle(myLocationStyle);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style

        //开始定位
        initLocation();
    }


    /**
     * 定位
     */
    private void initLocation() {
        mLocationClient = new AMapLocationClient(getActivity());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式
        //Hight_Accuracy为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(30000);
        mLocationOption.setInterval(60000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     *定位回调函数
     * */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        aMapLocation = amapLocation;
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                latitude = amapLocation.getLatitude();
                longitude = amapLocation.getLongitude();
                LatLng newLatlng = new LatLng(latitude, longitude);
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

                //获取定位信息
                StringBuffer buffer = new StringBuffer();
                buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
//                ToastUtils.showShortToast(getActivity(), buffer.toString());

                oldLatLng = newLatlng;
                if ("绘制".equals(locationFlag)) {
                    //绘制轨迹结束后重新定位
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            locationFlag = "定位";
                            aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latitude, longitude)));
                            mListener.onLocationChanged(aMapLocation);
//                            如果不取消注册，巡查模块会绘制两次轨迹
                            if (EventBus.getDefault().isRegistered(this)) {
                                EventBus.getDefault().unregister(this);
                            }
                            if (null != polyline && null != startMarker && null != endMarker){
                                //清除绘制的线、起点、终点图标marker
                                polyline.remove();
                                startMarker.remove();
                                endMarker.remove();
                            }

                        }
                    }, 20000);
                } else {
                    if (!EventBus.getDefault().isRegistered(this)) {
                        EventBus.getDefault().register(this);
                    }
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latitude, longitude)));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(amapLocation);

                }


                if (patrolFlag == 1) {   //巡查中
                    if (oldLatLng != newLatlng) {
                        setUpMap(oldLatLng, newLatlng);
                        oldLatLng = newLatlng;
                    }

                    String s = longitude + " " + latitude;
                    patrolList.add(s);
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                ToastUtils.showShortToast(getActivity(), "定位失败");
            }
        }
    }


    //缩放
    public void scaleLargeMap(LatLng nowLocation, float scaleValue) {
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nowLocation, scaleValue));
    }


    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    /**
     * 拍摄图片及视频
     */
    private void takePhotoOrVideo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.photo_or_video, null);
        builder.setView(view);
        TextView tv_photo = view.findViewById(R.id.tv_photo);
        TextView tv_video = view.findViewById(R.id.tv_video);
        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri imageUri;
                //验证sd卡是否可用
                if (CommonUtils.getSDPath() == null) {
                    Toast.makeText(getActivity(), "请安装SD卡", Toast.LENGTH_SHORT).show();
                    return;
                }
                File outputImage = new File(defaultPhotoAddress);
                //调用系统拍照
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(getActivity(),
                            "com.appler.riverchiefsystem.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
                show.dismiss();
            }
        });
        tv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.media.action.VIDEO_CAPTURE");
                intent.addCategory("android.intent.category.DEFAULT");
//                if (file.exists()) {
//                    file.delete();
//                }
                file = videoRename();
                Uri uri2;
                if (Build.VERSION.SDK_INT >= 24) {
                    uri2 = FileProvider.getUriForFile(getActivity(),
                            "com.appler.riverchiefsystem.fileprovider", file);
                } else {
                    uri2 = Uri.fromFile(file);
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri2);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);
//		                 intent.putExtra(MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED);
                startActivityForResult(intent, VIDEO_REQUEST_CODE);
                show.dismiss();
            }
        });
        show = builder.show();
    }


    /**
     * 视频重命名
     */
    private File videoRename() {
        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".mp4";
        //创建文件夹
        File out = new File(photoFolderAddress);
        if (!out.exists()) {
            out.mkdirs();
        }
        return new File(photoFolderAddress + File.separator + fileName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {  //图片

                //文件夹目录是否存在
                File folderAddr = new File(photoFolderAddress);
                if (!folderAddr.exists() || !folderAddr.isDirectory()) {
                    folderAddr.mkdirs();
                }
                //将原图片压缩拷贝到指定目录
                String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                final String targetPath = photoFolderAddress + File.separator + time + ".jpg";
                CommonUtils.dealImage(defaultPhotoAddress, targetPath);
                //删除原图
                new File(defaultPhotoAddress).delete();
                if (listFileNames == null) {
                    listFileNames = new ArrayList<>();
                }
                listFileNames.add(targetPath);
            } else if (requestCode == VIDEO_REQUEST_CODE) {   //视频
                if (listFileNames == null) {
                    listFileNames = new ArrayList<>();
                }
                listFileNames.add(photoFolderAddress + File.separator + fileName);
            } else if (requestCode == Global.PATROL_PHOTO_CODE) {  //巡查上报结束
                btnRiverPatrolEnd.setVisibility(View.GONE);
                btnRiverPatrolBegin.setVisibility(View.VISIBLE);
                tvRiverPatrolTakephoto.setVisibility(View.GONE);
                tvRiverPatrolReport.setVisibility(View.GONE);
                patrolFlag = 0;
                tvRiverPatrolTitle.setText("巡查");
            }
        }
    }


    /**
     * EventBus监听
     * 巡查记录点击后显示巡查轨迹 动态绘制
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(EventBusData eventBusData) {
        locationFlag = "绘制";
        final String gpsTrack = eventBusData.getGpsTrack();
        final String heduanName = eventBusData.getHeduanName();
        final String riverName = eventBusData.getRiverName();
        final String type = eventBusData.getType();
        switch (eventBusData.getMessage()) {
            case "riverGpsTrack":  //河湖信息
                showTrack(gpsTrack, type, riverName, heduanName);
                break;
            case "patrolGpsTrack"://巡查
                showTrack(gpsTrack, type, riverName, heduanName);
                break;
            case "problemLocation":   //问题上报 定位 添加marker点
                String mid = eventBusData.getProblemId();
                if (null != gpsTrack && !"".equals(gpsTrack) && !"null".equals(gpsTrack)) {
                    if (gpsTrack.contains(",")) {
                        String[] split = gpsTrack.split(",");
                        //坐标转换 将WGS84坐标转换为高德坐标
                        LatLng proLatlng = CoordinateUtil.GPSToGaode(getActivity(), new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0])));
                        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(proLatlng, 17));
                        //上报问题条目添加 marker
                        addProMarker(proLatlng, riverName, heduanName, mid);
                    }
                }
                break;
        }
    }

    /**
     * 显示轨迹
     * 河湖信息只显示定位线，添加起始marker点
     * 巡查记录条目显示定位线，添加一个imageview动态绘制，按照顺序
     * */
    private void showTrack(String gpsTrack, String type, String riverName, String heduanName) {
        List<LatLng> points = new ArrayList<>();
        if (gpsTrack.contains(",")) {
            String[] split = gpsTrack.split(",");
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                if (s.contains(" ")) {
                    String[] s1 = s.split(" ");
                    points.add(new LatLng(Double.parseDouble(s1[1]), Double.parseDouble(s1[0])));
                }
            }
        }

        //起点位置和  地图界面大小控制
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), aMap.getCameraPosition().zoom));
//        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(points.get(points.size() / 2), aMap.getCameraPosition().zoom));
        //用一个数组来存放纹理
        List<BitmapDescriptor> texturesList = new ArrayList<>();
        texturesList.add(BitmapDescriptorFactory.fromResource(R.drawable.ic_arrow_right));
        aMap.setMapTextZIndex(2);  //将地图底图文字设置在添加的覆盖物之上
        polyline = aMap.addPolyline((new PolylineOptions())
                .addAll(points) //集合数据
                .width(20) //线的宽度
                .setDottedLine(true)//设置是否为虚线
                .geodesic(false)//是否为大地曲线
                .visible(true)//线段是否可见
                .useGradient(false)//设置线段是否使用渐变色
                .setCustomTextureList(texturesList)   //设置纹理
                .color(Color.argb(255, 255, 20, 147)));//颜色


        //动态绘制线
        if (points.size() >= 3) {

            LatLng endLatlng = points.get(points.size() - 1);
            LatLng startLatlng = points.get(0);
            //起点图标
            startMarker = aMap.addMarker(new MarkerOptions()
                    .position(AMapUtil.convertToLatLng(new LatLonPoint(startLatlng.latitude, startLatlng.longitude)))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.mapstart)));

            //终点坐标
            LatLonPoint latLonPointEnd = new LatLonPoint(endLatlng.latitude, endLatlng.longitude);
            endMarker = aMap.addMarker(new MarkerOptions()
                    .position(AMapUtil.convertToLatLng(latLonPointEnd))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.mapend)));

            LatLngBounds bounds = new LatLngBounds(points.get(points.size() / 2), points.get(points.size() - 1));
            aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));


            if ("巡查".equals(type)) {    //巡查 动态显示绘制轨迹，其他（河湖信息）只显示轨迹
                SmoothMoveMarker smoothMarker = new SmoothMoveMarker(aMap);
                // 设置滑动的图标
                smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.mipmap.person));
                LatLng drivePoint = points.get(0);
                Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(points, drivePoint);
                points.set(pair.first, drivePoint);
                List<LatLng> subList = points.subList(pair.first, points.size());
                // 设置滑动的轨迹坐标点
                smoothMarker.setPoints(subList);
                // 设置滑动的总时间
                smoothMarker.setTotalDuration(10);
                // 开始滑动
                smoothMarker.startSmoothMove();
            }
        } else {
            ToastUtils.showShortToast(getActivity(), "轨迹太短");
        }


    }


    /**
     * 绘制两个坐标点之间的线段,从以前位置到现在位置
     */
    private void setUpMap(LatLng oldData, LatLng newData) {
        // 绘制一个大地曲线
        aMap.addPolyline((new PolylineOptions())
                .add(oldData, newData)
                .geodesic(true)
                .color(Color.GREEN));

    }



    /**
     * 高德地图添加marker   上报问题条目点击后
     */
    public void addProMarker(LatLng latLng, String proReason, String proRemark, String mid) {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View view = mInflater.inflate(R.layout.problem_marker_layout, null);
        TextView tv_reason = view.findViewById(R.id.tv_reason);
        TextView tv_remark = view.findViewById(R.id.tv_remark);
        tv_reason.setText(proReason);
        tv_remark.setText(proRemark);

        Bitmap bitmap = convertViewToBitmap(view);
        //绘制marker
        marker = aMap.addMarker(new MarkerOptions()
                .position(latLng)
                .period(Integer.valueOf(mid))//添加markerID
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                .draggable(false));

        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(getActivity(), ProblemDetailActivity.class);
                intent.putExtra("msgId", String.valueOf(marker.getPeriod()));
                getActivity().startActivity(intent);
                clearMarkers();
                return false;
            }
        });
    }

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;

    }


    //删除指定Marker
    private void clearMarkers() {
        marker.remove();//移除当前Marker
        //获取地图上所有Marker  使用这个方法，
        List<Marker> mapScreenMarkers = aMap.getMapScreenMarkers();
        for (int i = 0; i < mapScreenMarkers.size(); i++) {
            Marker marker = mapScreenMarkers.get(i);
//                marker.remove();//移除当前Marker
        }
    }


    /**
     * 显示不同图层
     */
    private boolean flag = false;
    private void showLayer(String mapName, ImageView imageView, TextView textView, int amapType) {
        ivWxt.setBackgroundResource(R.mipmap.btn_yx_normal);
        ivYjdt.setBackgroundResource(R.mipmap.btn_sl_normal);
        ivDhdt.setBackgroundResource(R.mipmap.btn_dl_normal);
        tvWxt.setTextColor(Color.parseColor("#444444"));
        tvYjdt.setTextColor(Color.parseColor("#444444"));
        tvDhdt.setTextColor(Color.parseColor("#444444"));

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isAvailable()) {
            if (flag == true) {
                if ("卫星图".equals(mapName)) {
                    imageView.setBackgroundResource(R.mipmap.btn_yx_normal);
                } else if ("夜景地图".equals(mapName)) {
                    imageView.setBackgroundResource(R.mipmap.btn_sl_normal);
                } else if ("导航地图".equals(mapName)) {
                    imageView.setBackgroundResource(R.mipmap.btn_dl_normal);
                }
                textView.setTextColor(Color.parseColor("#444444"));
                flag = false;
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);
            } else {
                if ("卫星图".equals(mapName)) {
                    imageView.setBackgroundResource(R.mipmap.btn_yx_pressed);
                } else if ("夜景地图".equals(mapName)) {
                    imageView.setBackgroundResource(R.mipmap.btn_sl_pressed);
                } else if ("导航地图".equals(mapName)) {
                    imageView.setBackgroundResource(R.mipmap.btn_dl_pressed);
                }
                textView.setTextColor(Color.parseColor("#1E9FFF"));
                flag = true;
                aMap.setMapType(amapType);
            }
        } else {
            Toast.makeText(getActivity(), "网络连接失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (null != mapView) {
            mapView.onDestroy();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
