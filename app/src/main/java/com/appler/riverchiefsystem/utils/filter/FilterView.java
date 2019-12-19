package com.appler.riverchiefsystem.utils.filter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterView extends LinearLayout implements View.OnClickListener {
    public static final int POSITION_CATEGORY = 0; // 分类的位置
    public static final int POSITION_TYPE_FILTER = 1; // 筛选的位置  类型
    public static final int POSITION_SORT = 2; // 排序的位置
    public static final int POSITION_ORG_FILTER = 3; // 筛选的位置
    public static final int POSITION_FILTER = 4; // 筛选的位置
    private static final String TAG = "FilterView";
    @BindView(R.id.tv_category_title)
    TextView tvCategoryTitle;
    @BindView(R.id.iv_category_arrow)
    ImageView ivCategoryArrow;
    @BindView(R.id.tv_sort_title)
    TextView tvSortTitle;
    @BindView(R.id.iv_sort_arrow)
    ImageView ivSortArrow;
    @BindView(R.id.tv_org_filter_title)
    TextView tvOrgFilterTitle;
    @BindView(R.id.iv_org_filter_arrow)
    ImageView ivOrgFilterArrow;
    @BindView(R.id.tv_filter_title)
    TextView tvFilterTitle;
    @BindView(R.id.iv_filter_arrow)
    ImageView ivFilterArrow;
    @BindView(R.id.ll_category)
    LinearLayout llCategory;
    @BindView(R.id.ll_sort)
    LinearLayout llSort;
    @BindView(R.id.ll_org_filter)
    LinearLayout ll_org_filter;
    @BindView(R.id.ll_filter)
    LinearLayout llFilter;
    @BindView(R.id.lv_left)
    ListView lvLeft;
    @BindView(R.id.lv_right)
    ListView lvRight;
    @BindView(R.id.ll_head_layout)
    LinearLayout llHeadLayout;
    @BindView(R.id.ll_content_list_view)
    LinearLayout llContentListView;
    @BindView(R.id.view_mask_bg)
    View viewMaskBg;
    @BindView(R.id.tv_types_title)
    TextView tvTypesTitle;
    @BindView(R.id.iv_types_arrow)
    ImageView ivTypesArrow;
    @BindView(R.id.ll_types)
    LinearLayout llTypes;
    private Context mContext;
    private Activity mActivity;
    private int filterPosition = -1;
    private int lastFilterPosition = -1;
    private boolean isShowing = false;
    private int panelHeight;
    private FilterData filterData;

    private FilterLeftAdapter leftAdapter;
    private FilterRightAdapter rightAdapter;
    private FilterOneAdapter sortAdapter;
    private FilterOneAdapter filterOrgAdapter;
    private FilterOneAdapter filterAdapter;
    private FilterOneAdapter typesAdapter;

    private FilterTwoEntity leftSelectedCategoryEntity; // 被选择的分类项左侧数据
    private FilterEntity rightSelectedCategoryEntity; // 被选择的分类项右侧数据
    private FilterEntity selectedSortEntity; // 被选择的排序项
    private FilterEntity selectedOrgFilterEntity; // 被选择的科室筛选项
    private FilterEntity selectedFilterEntity; // 被选择的筛选项
    private FilterEntity selectedTypesEntity; // 被选择的筛选项  类型
    // 筛选视图点击
    private OnFilterClickListener onFilterClickListener;
    // 分类Item点击
    private OnItemCategoryClickListener onItemCategoryClickListener;
    // 排序Item点击
    private OnItemSortClickListener onItemSortClickListener;
    // 筛选Item点击
    private OnItemFilterClickListener onItemFilterClickListener;
    // 科室筛选Item点击
    private OnItemOrgFilterClickListener onItemOrgFilterClickListener;
    // 类型筛选Item点击
    private OnItemTypesClickListener onItemTypesClickListener;

    public FilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // 旋转箭头向上
    public static void rotateArrowUpAnimation(final ImageView iv) {
        if (iv == null) return;
        RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        iv.startAnimation(animation);
    }

    // 旋转箭头向下
    public static void rotateArrowDownAnimation(final ImageView iv) {
        if (iv == null) return;
        RotateAnimation animation = new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        iv.startAnimation(animation);
    }

    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_filter_layout, this);
        ButterKnife.bind(this, view);

        initView();
        initListener();
    }

    private void initView() {
        viewMaskBg.setVisibility(GONE);
        llContentListView.setVisibility(GONE);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        llCategory.setOnClickListener(this);
        llSort.setOnClickListener(this);
        ll_org_filter.setOnClickListener(this);
        llFilter.setOnClickListener(this);
        llTypes.setOnClickListener(this);
        viewMaskBg.setOnClickListener(this);
        llContentListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_category:
                filterPosition = 0;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_types:
                filterPosition = 1;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_sort:
                filterPosition = 2;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_org_filter:
                filterPosition = 3;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_filter:
                filterPosition = 4;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.view_mask_bg:
                hide();
                break;
        }
    }

    // 复位筛选的显示状态
    public void resetViewStatus() {
        tvCategoryTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivCategoryArrow.setImageResource(R.mipmap.home_down_arrow);

        tvTypesTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivTypesArrow.setImageResource(R.mipmap.home_down_arrow);

        tvSortTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivSortArrow.setImageResource(R.mipmap.home_down_arrow);

        tvOrgFilterTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivOrgFilterArrow.setImageResource(R.mipmap.home_down_arrow);

        tvFilterTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivFilterArrow.setImageResource(R.mipmap.home_down_arrow);
    }

    // 复位所有的状态
    public void resetAllStatus() {
        resetViewStatus();
        hide();
    }

    // 设置分类数据
    private void setCategoryAdapter() {

        lvLeft.setVisibility(VISIBLE);
        lvRight.setVisibility(VISIBLE);
        // 左边列表视图
        leftAdapter = new FilterLeftAdapter(mContext, filterData.getCategory());
        lvLeft.setAdapter(leftAdapter);
        if (leftSelectedCategoryEntity == null) {
            leftSelectedCategoryEntity = filterData.getCategory().get(0);
        }
//        leftAdapter.setSelectedEntity(leftSelectedCategoryEntity);   //20190226+

        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                leftSelectedCategoryEntity = filterData.getCategory().get(position);
                leftAdapter.setSelectedEntity(leftSelectedCategoryEntity);

                // 右边列表视图
                rightAdapter = new FilterRightAdapter(mContext, leftSelectedCategoryEntity.getList());
                lvRight.setAdapter(rightAdapter);
                rightAdapter.setSelectedEntity(rightSelectedCategoryEntity);
            }
        });

        // 右边列表视图
        rightAdapter = new FilterRightAdapter(mContext, leftSelectedCategoryEntity.getList());
        lvRight.setAdapter(rightAdapter);
        rightAdapter.setSelectedEntity(rightSelectedCategoryEntity);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rightSelectedCategoryEntity = leftSelectedCategoryEntity.getList().get(position);
                rightAdapter.setSelectedEntity(rightSelectedCategoryEntity);
                if (onItemCategoryClickListener != null) {
                    onItemCategoryClickListener.onItemCategoryClick(leftSelectedCategoryEntity, rightSelectedCategoryEntity);
                }
                hide();
            }
        });
    }

    // 设置类型数据
    private void setTypesAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        typesAdapter = new FilterOneAdapter(mContext, filterData.getTypes());
        lvRight.setAdapter(typesAdapter);

        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTypesEntity = filterData.getTypes().get(position);
                typesAdapter.setSelectedEntity(selectedTypesEntity);
                if (onItemTypesClickListener != null) {
                    onItemTypesClickListener.onItemTypesClick(selectedTypesEntity);
                }
                hide();
            }
        });
    }
    // 设置排序数据
    private void setSortAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        sortAdapter = new FilterOneAdapter(mContext, filterData.getSorts());
        lvRight.setAdapter(sortAdapter);

        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSortEntity = filterData.getSorts().get(position);
                sortAdapter.setSelectedEntity(selectedSortEntity);
                if (onItemSortClickListener != null) {
                    onItemSortClickListener.onItemSortClick(selectedSortEntity);
                }
                hide();
            }
        });
    }

    // 设置科室筛选数据
    private void setOrgFilterAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);
        filterOrgAdapter = new FilterOneAdapter(mContext, filterData.getOrgFilter());
        lvRight.setAdapter(filterOrgAdapter);

        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOrgFilterEntity = filterData.getOrgFilter().get(position);
                filterOrgAdapter.setSelectedEntity(selectedOrgFilterEntity);
                if (onItemOrgFilterClickListener != null) {
                    onItemOrgFilterClickListener.onItemOrgFilterClick(selectedOrgFilterEntity);
                }
                hide();
            }
        });


    }

    // 设置筛选数据
    private void setFilterAdapter() {
        lvLeft.setVisibility(GONE);


        lvRight.setVisibility(VISIBLE);

        filterAdapter = new FilterOneAdapter(mContext, filterData.getFilters());
        lvRight.setAdapter(filterAdapter);

        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFilterEntity = filterData.getFilters().get(position);
                filterAdapter.setSelectedEntity(selectedFilterEntity);
                if (onItemFilterClickListener != null) {
                    onItemFilterClickListener.onItemFilterClick(selectedFilterEntity);
                }
                hide();
            }
        });


    }

    // 动画显示
    public void show(int position) {
        if (isShowing && lastFilterPosition == position) {
            hide();
            return;
        } else if (!isShowing) {
            viewMaskBg.setVisibility(VISIBLE);
            llContentListView.setVisibility(VISIBLE);
            llContentListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    llContentListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    panelHeight = llContentListView.getHeight();
                    ObjectAnimator.ofFloat(llContentListView, "translationY", -panelHeight, 0).setDuration(200).start();
                }
            });
        }
        isShowing = true;
        resetViewStatus();
        rotateArrowUp(position);
        rotateArrowDown(lastFilterPosition);
        lastFilterPosition = position;

        switch (position) {
            case POSITION_CATEGORY:
                tvCategoryTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                ivCategoryArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setCategoryAdapter();
                break;
            case POSITION_TYPE_FILTER:
                tvTypesTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                ivTypesArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setTypesAdapter();
                break;
            case POSITION_SORT:
                tvSortTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                ivSortArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setSortAdapter();
                break;
            case POSITION_ORG_FILTER:
                tvOrgFilterTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                ivOrgFilterArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setOrgFilterAdapter();
                break;
            case POSITION_FILTER:
                tvFilterTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                ivFilterArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setFilterAdapter();
                break;
        }
    }

    // 隐藏动画
    public void hide() {
        isShowing = false;
        resetViewStatus();
        rotateArrowDown(filterPosition);
        rotateArrowDown(lastFilterPosition);
        filterPosition = -1;
        lastFilterPosition = -1;
        viewMaskBg.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(llContentListView, "translationY", 0, -panelHeight).setDuration(200).start();
    }

    // 设置筛选数据
    public void setFilterData(Activity activity, FilterData filterData) {
        this.mActivity = activity;
        this.filterData = filterData;
        if (filterData.getCategory() == null) {
            llCategory.setVisibility(GONE);
        }
        if (filterData.getTypes() == null) {
            llTypes.setVisibility(GONE);
        }
        if (filterData.getSorts() == null) {
            llSort.setVisibility(GONE);
        }
        if (filterData.getOrgFilter() == null) {
            ll_org_filter.setVisibility(GONE);
        }
        if (filterData.getFilters() == null) {
            llFilter.setVisibility(GONE);
        }

    }

    // 是否显示
    public boolean isShowing() {
        return isShowing;
    }

    public int getFilterPosition() {
        return filterPosition;
    }

    // 旋转箭头向上
    private void rotateArrowUp(int position) {
        switch (position) {
            case POSITION_CATEGORY:
                rotateArrowUpAnimation(ivCategoryArrow);
                break;
            case POSITION_TYPE_FILTER:
                rotateArrowUpAnimation(ivTypesArrow);
                break;
            case POSITION_SORT:
                rotateArrowUpAnimation(ivSortArrow);
                break;
            case POSITION_ORG_FILTER:
                rotateArrowUpAnimation(ivOrgFilterArrow);
                break;
            case POSITION_FILTER:
                rotateArrowUpAnimation(ivFilterArrow);
                break;
        }
    }

    // 旋转箭头向下
    private void rotateArrowDown(int position) {
        switch (position) {
            case POSITION_CATEGORY:
                rotateArrowDownAnimation(ivCategoryArrow);
                break;
            case POSITION_TYPE_FILTER:
                rotateArrowDownAnimation(ivTypesArrow);
                break;
            case POSITION_SORT:
                rotateArrowDownAnimation(ivSortArrow);
                break;
            case POSITION_ORG_FILTER:
                rotateArrowDownAnimation(ivOrgFilterArrow);
                break;
            case POSITION_FILTER:
                rotateArrowDownAnimation(ivFilterArrow);
                break;
        }
    }

    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }

    public void setOnItemCategoryClickListener(OnItemCategoryClickListener onItemCategoryClickListener) {
        this.onItemCategoryClickListener = onItemCategoryClickListener;
    }

    public void setOnItemSortClickListener(OnItemSortClickListener onItemSortClickListener) {
        this.onItemSortClickListener = onItemSortClickListener;
    }

    public void setOnItemFilterClickListener(OnItemFilterClickListener onItemFilterClickListener) {
        this.onItemFilterClickListener = onItemFilterClickListener;
    }

    public void setOnItemOrgFilterClickListener(OnItemOrgFilterClickListener onItemOrgFilterClickListener) {
        this.onItemOrgFilterClickListener = onItemOrgFilterClickListener;
    }

    public void setOnItemTypesClickListener(OnItemTypesClickListener onItemTypesClickListener) {
        this.onItemTypesClickListener = onItemTypesClickListener;
    }

    public interface OnFilterClickListener {
        void onFilterClick(int position);
    }

    public interface OnItemCategoryClickListener {
        void onItemCategoryClick(FilterTwoEntity leftEntity, FilterEntity rightEntity);
    }


    public interface OnItemSortClickListener {
        void onItemSortClick(FilterEntity entity);
    }

    public interface OnItemFilterClickListener {
        void onItemFilterClick(FilterEntity entity);
    }

    public interface OnItemOrgFilterClickListener {
        void onItemOrgFilterClick(FilterEntity entity);
    }
    public interface OnItemTypesClickListener {
        void onItemTypesClick(FilterEntity entity);
    }

}
