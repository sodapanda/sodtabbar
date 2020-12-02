package fit.soda.sodtabbar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fit.soda.sodtabbar.model.TabListModel;
import fit.soda.sodtabbar.model.TabModel;

public class TabLayout extends FrameLayout {
    private Context mContext;
    private LinearLayout mHolderView;
    private ViewPager mViewPager;
    public TabModel mModel;
    private Map<String, TabItemLayout> typeToView;
    private List<TabItemLayout> itemViewList;
    private List<String> contextTypeList;
    private List<String> routerList;
    public FragmentAdapter mAdapter;
    private TabItemLayout current;
    private SelectListener mListener;
    public static TabLayout instance;

    public TabLayout(@NonNull Context context) {
        this(context, null);
    }

    public TabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        instance = this;
        LayoutInflater.from(context).inflate(R.layout.gj_tab_layout, this, true);
    }

    public void setData(TabModel model, FragmentAdapter adapter) {
        this.mModel = model;
        typeToView = new HashMap<>();
        itemViewList = new ArrayList<>();
        contextTypeList = new ArrayList<>();
        routerList = new ArrayList<>();
        this.mAdapter = adapter;
        setupViews();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHolderView = findViewById(R.id.tab_holder);
        mViewPager = findViewById(R.id.tab_content_viewpager);
    }

    public void setSelectListener(SelectListener listener) {
        this.mListener = listener;
    }

    private void setupViews() {
        mHolderView.removeAllViews();
        TabItemLayout defaultItem = null;
        for (final TabListModel model : mModel.tabList) {
            TabItemLayout itemLayout = new TabItemLayout(mContext);
            itemLayout.mFullTab = TabLayout.this;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            itemLayout.setLayoutParams(params);
            mHolderView.addView(itemLayout);
            itemLayout.setData(model);
            typeToView.put(model.contextType, itemLayout);
            itemViewList.add(itemLayout);
            contextTypeList.add(model.contextType);
            routerList.add(model.contentUrl);
            if (model.isDefault) {
                defaultItem = itemLayout;
            }
            itemLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        boolean intercept = mListener.select(model);
                        if (!intercept) {
                            active((TabItemLayout) view, model.contextType);
                        }
                    } else {
                        active((TabItemLayout) view, model.contextType);
                    }
                }
            });
        }

        setupViewPager();
        activeDefault(defaultItem);
    }

    private void setupViewPager() {
        mViewPager.setOffscreenPageLimit(10);
        mViewPager.setAdapter(new FragAdapter(mAdapter.getFragmentManager()));
    }

    private void activeDefault(TabItemLayout item) {
        current = item;
        mViewPager.setCurrentItem(getIndexByView(item));
    }

    public void activateTab(String contextType) {
        active(typeToView.get(contextType), contextType);
    }

    private String getRouterByIndex(int index) {
        return routerList.get(index);
    }

    private int getIndexByView(TabItemLayout view) {
        return itemViewList.indexOf(view);
    }

    void active(TabItemLayout item, String ct) {
        if (item == null) {
            return;
        }
        if (current != null) {
            current.inactive();
            mViewPager.setCurrentItem(getIndexByView(item), false);
        }
        current = item;
        item.active();
        if (mListener != null) {
            mListener.afterSelect(ct);
        }
    }

    public TabListModel getCurrentTab() {
        return mModel.tabList.get(getIndexByView(current));
    }

    class FragAdapter extends FragmentPagerAdapter {

        public FragAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String router = getRouterByIndex(position);
            return mAdapter.getFragment(router);
        }

        @Override
        public int getCount() {
            return itemViewList.size();
        }
    }
}