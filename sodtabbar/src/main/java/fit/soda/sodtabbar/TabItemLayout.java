package fit.soda.sodtabbar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import fit.soda.sodtabbar.model.TabListModel;

public class TabItemLayout extends FrameLayout {
    private Context mContext;
    private ImageView mIcon;
    private TextView mTitleTv;
    private TabListModel mModel;
    private View mReddot;
    public TabLayout mFullTab;

    public TabItemLayout(Context context) {
        this(context, null);
    }

    public TabItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.gj_tab_item_layout, this, true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mIcon != null) {
            return;
        }
        mIcon = findViewById(R.id.tab_icon);
        mTitleTv = findViewById(R.id.tab_title);
        mReddot = findViewById(R.id.tab_red_dot);

        if (mModel != null) {
            setupViews();
        }
    }

    public void setData(TabListModel model) {
        mModel = model;
        if (mTitleTv != null) {
            setupViews();
        }
    }

    private void setupViews() {
        mTitleTv.setText(mModel.title);

        Glide.with(mContext)
                .load(mModel.inActiveIcon)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(mIcon);

        if (mModel.isDefault) {
            active();
        } else {
            inactive();
        }

        if (mModel.redDot) {
            mReddot.setVisibility(VISIBLE);
        } else {
            mReddot.setVisibility(GONE);
        }
    }

    public void active() {
        int color = Color.parseColor(mModel.activeTitleColor);
        mTitleTv.setTextColor(color);
        Glide.with(mContext)
                .load(mModel.activeIcon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .fitCenter()
                .into(mIcon);
        if (mModel.redDot) {
            mModel.redDot = false;
            mReddot.setVisibility(GONE);
            mModel.redDotClickTime = System.currentTimeMillis() / 1000L;
            mFullTab.mAdapter.saveModel(mFullTab.mModel);
        }
    }

    public void inactive() {
        int color = Color.parseColor(mModel.inactiveTitleColor);
        mTitleTv.setTextColor(color);
        Glide.with(mContext)
                .load(mModel.inActiveIcon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .fitCenter()
                .into(mIcon);
    }

}
