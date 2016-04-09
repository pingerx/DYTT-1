package com.bzh.dytt.tv;

import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic.IFragmentPresenter;
import com.bzh.dytt.base.tablayoutview.TabLayoutFragment;
import com.bzh.dytt.base.tablayoutview.TabLayoutPresenter;

import java.util.ArrayList;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　音悦台 版权所有(c)2016<br>
 * <b>作者</b>：　　  zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　16-3-21<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class TvMainPresenter extends TabLayoutPresenter implements IFragmentPresenter {

    public static final String GCHP = "XBZY";
    private static final String GT = "GT";
    private static final String HY = "HY";
    private static final String RH = "RH";
    private static final String OM = "OM";

    public TvMainPresenter(BaseActivity baseActivity, BaseFragment baseFragment, TabLayoutFragment filmMainIView) {
        super(baseActivity, baseFragment, filmMainIView);
    }

    @Override
    public BaseFragment newFragment(StripTabItem stripTabItem) {
        switch (stripTabItem.getType()) {
            case GCHP:
                return GCHPTvFragment.newInstance();
            case GT:
                return GTTvFragment.newInstance();
            case HY:
                return HYTvFragment.newInstance();
            case RH:
                return RHTvFragment.newInstance();
            case OM:
                return OMTvFragment.newInstance();
        }
        return GCHPTvFragment.newInstance();
    }

    @Override
    public ArrayList<StripTabItem> generateTabs() {
        ArrayList<StripTabItem> items = new ArrayList<>();
        items.add(new StripTabItem(GCHP, "国产合拍"));
        items.add(new StripTabItem(GT, "港台"));
        items.add(new StripTabItem(HY, "华语"));
        items.add(new StripTabItem(RH, "日韩"));
        items.add(new StripTabItem(OM, "欧美"));
        return items;
    }
}
