package com.xxc.dev.common.widgets.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import com.xxc.dev.common.utils.DisplayUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * 无限轮循的ViewPagerAdapter
 * 内部固定5个itemView，用来无限循环，避免重复创建view视图的操作，节省内存
 * 不支持多种type，如果要支持多种type，需要添加type接口，考虑到banner很少会有多种type的情况，暂不扩充
 */
public class BannerPagerAdapter extends PagerAdapter implements OnPageChangeListener {

    public static final String TAG = "BannerPagerAdapter";

    /**
     * itemView的最大缓存数量
     */
    public static final int MAX_CACHE_ITEM_COUNT = 5;

    private List<View> mItems = new ArrayList<>();
    private PagerItemInflater mItemInflater;
    private List<ImageView> mCursorViews = new ArrayList<>();
    private CursorSelector mSelector;

    public BannerPagerAdapter(@NonNull PagerItemInflater itemInflater, @NonNull ViewGroup cursorContainer) {
//        test check
        mItemInflater = itemInflater;
        int count = mItemInflater.modelCounts();
        mSelector = itemInflater.cursorSelector();
        cursorContainer.removeAllViews();
        if (count > 1 && null != mSelector) {
            cursorContainer.setVisibility(View.VISIBLE);
            Context context = cursorContainer.getContext();
            for (int i = 0; i < count; i++) {
                ImageView cursorView = new ImageView(context);
                LayoutParams params = new LayoutParams(DisplayUtils.dip2px(context, 18), DisplayUtils.dip2px(context, 3));
                params.leftMargin = DisplayUtils.dip2px(context, 3);
                params.rightMargin = DisplayUtils.dip2px(context, 3);
                int imageRes = i == 0 ? mSelector.getSelectedRes() : mSelector.getUnselectedRes();
                cursorView.setImageResource(imageRes);
                cursorContainer.addView(cursorView, params);
                mCursorViews.add(cursorView);
            }
        } else {
            cursorContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getCount() {
        if (mItemInflater.modelCounts() == 1) {
            return 1;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int modelPosition = modelPosition(position);
        int innerPosition = innerPosition(position);
        View itemView;
        if (mItems.size() < MAX_CACHE_ITEM_COUNT) {
            itemView = mItemInflater.onRefreshItemView(null, container, modelPosition);
            mItems.add(itemView);
        } else {
            itemView = mItems.get(innerPosition);
            mItemInflater.onRefreshItemView(itemView, container, modelPosition);
        }
        if (itemView.getParent() != null) {
            ((ViewGroup) itemView.getParent()).removeView(itemView);
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //        super.destroyItem(container,position,object);
        int innerPosition = innerPosition(position);
        View itemView = mItems.get(innerPosition);
        Object group = itemView.getParent();
        if (group instanceof ViewGroup) {
            ((ViewGroup) group).removeView(itemView);
        }
        container.removeView(itemView);
    }

    private int innerPosition(int position) {
        if (mItems.size() == 0) {
            return 0;
        }
        return position % mItems.size();
    }

    private int modelPosition(int position) {
        if (mItemInflater.modelCounts() == 0) {
            return 0;
        }
        return position % mItemInflater.modelCounts();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int modelPosition = modelPosition(position);
        mItemInflater.onItemSelected(modelPosition);
        int count = mItemInflater.modelCounts();
        Log.d(TAG, "onPageSelected: " + modelPosition + "  size:" + mCursorViews.size() + "  count:" + count);
        if (count == mCursorViews.size() && null != mSelector) {
            for (int i = 0; i < count; i++) {
                ImageView cursorView = mCursorViews.get(i);
                if (i == modelPosition) {
                    cursorView.setImageResource(mSelector.getSelectedRes());
                } else {
                    cursorView.setImageResource(mSelector.getUnselectedRes());
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
