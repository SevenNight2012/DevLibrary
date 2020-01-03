package com.xxc.dev.common.widgets.banner;

import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * 主要用于在无限循环的ViewPager中提供数据，渲染，刷新视图，
 * 类似BaseAdapter，所有视图在ViewPagerAdapter内部已经渲染好，只需要根据不同的数据进行视图刷新即可
 */
public interface PagerItemInflater {

    /**
     * 数据集大小
     *
     * @return 集合大小
     */
    int modelCounts();

    /**
     * 主要用于刷新视图
     *
     * @param itemView 内部已经创建好的视图对象
     * @param position position位置
     */
    @NonNull
    View onRefreshItemView(View itemView, ViewGroup parent, int position);

    /**
     * 当前选中项
     *
     * @param position 索引值
     */
    void onItemSelected(int position);

    /**
     * 返回一个指示器资源类
     *
     * @return 指示器资源对象
     */
    CursorSelector cursorSelector();
}
