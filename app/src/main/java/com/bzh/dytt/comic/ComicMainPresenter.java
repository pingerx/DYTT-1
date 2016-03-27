package com.bzh.dytt.comic;

import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic.IFragmentPresenter;
import com.bzh.dytt.base.tablayoutview.TabLayoutFragment;
import com.bzh.dytt.base.tablayoutview.TabLayoutPresenter;
import com.bzh.dytt.variety.JBZYVarietyFragment;
import com.bzh.dytt.variety.XBDLVarietyFragment;
import com.bzh.dytt.variety.XBGTVarietyFragment;
import com.bzh.dytt.variety.XBQTVarietyFragment;
import com.bzh.dytt.variety.XBZYVarietyFragment;

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
public class ComicMainPresenter extends TabLayoutPresenter implements IFragmentPresenter {

    private static final String DM = "DM";
    private static final String XF = "XF";
    private static final String GC = "GC";
    private static final String QT = "QT";
    private static final String SS = "SS";
    private static final String HZW = "HZW";
    private static final String HY = "HY";

    public ComicMainPresenter(BaseActivity baseActivity, BaseFragment baseFragment, TabLayoutFragment filmMainIView) {
        super(baseActivity, baseFragment, filmMainIView);
    }

    @Override
    public BaseFragment newFragment(StripTabItem stripTabItem) {
        switch (stripTabItem.getType()) {
            case DM: // 动漫
                return DMComicFragment.newInstance();
            case XF: // 新番
                return XFComicFragment.newInstance();
            case GC: // 国产
                return GCComicFragment.newInstance();
            case QT: // 其他
                return QTComicFragment.newInstance();
            case SS: // 死神
                return SSComicFragment.newInstance();
            case HZW: // 海贼王
                return HZWComicFragment.newInstance();
            case HY: // 火影
                return HYComicFragment.newInstance();
        }
        throw new RuntimeException("未知错误");
    }

    @Override
    public ArrayList<StripTabItem> generateTabs() {
        ArrayList<StripTabItem> items = new ArrayList<>();
        items.add(new StripTabItem(DM, "动漫"));
        items.add(new StripTabItem(XF, "新番动漫"));
        items.add(new StripTabItem(GC, "国产动漫"));
        items.add(new StripTabItem(QT, "其他动漫"));
        items.add(new StripTabItem(SS, "死神专区"));
        items.add(new StripTabItem(HZW, "海贼王专区"));
        items.add(new StripTabItem(HY, "火影专区"));
        return items;
    }
}
