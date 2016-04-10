package com.bzh.dytt.variety;

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
public class VarietyMainPresenter extends TabLayoutPresenter implements IFragmentPresenter {

    private static final String XBZY = "XBZY";
    private static final String JBZY = "JBZY";
    private static final String XBDL = "XBDL";
    private static final String XBGT = "GT";
    private static final String XBQT = "XBQT";

    public VarietyMainPresenter(BaseActivity baseActivity, BaseFragment baseFragment, TabLayoutFragment filmMainIView) {
        super(baseActivity, baseFragment, filmMainIView);
    }

    @Override
    public BaseFragment newFragment(StripTabItem stripTabItem) {
        switch (stripTabItem.getType()) {
            case XBZY: // 新版综艺
                return XBZYVarietyFragment.newInstance();
            case XBDL: // 新版大陆
                return XBDLVarietyFragment.newInstance();
            case XBGT: // 新版港台
                return XBGTVarietyFragment.newInstance();
            case XBQT: // 新版其他
                return XBQTVarietyFragment.newInstance();
            case JBZY: // 旧版综艺
                return JBZYVarietyFragment.newInstance();
        }
        throw new RuntimeException("未知错误");
    }

    @Override
    public ArrayList<StripTabItem> generateTabs() {
        ArrayList<StripTabItem> items = new ArrayList<>();
        items.add(new StripTabItem(XBZY, "新版综艺"));
        items.add(new StripTabItem(XBDL, "新版大陆"));
        items.add(new StripTabItem(XBGT, "新版港台"));
        items.add(new StripTabItem(XBQT, "新版其他"));
        items.add(new StripTabItem(JBZY, "旧版综艺"));
        return items;
    }
}
