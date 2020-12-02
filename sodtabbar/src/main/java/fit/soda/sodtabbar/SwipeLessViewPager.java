package fit.soda.sodtabbar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wangxiao on 2017/11/22.
 */

public class SwipeLessViewPager extends ViewPager {
    public SwipeLessViewPager(Context context) {
        super(context);
    }

    public SwipeLessViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
