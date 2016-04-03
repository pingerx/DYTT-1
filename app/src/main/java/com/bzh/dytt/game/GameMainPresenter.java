package com.bzh.dytt.game;

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
public class GameMainPresenter extends TabLayoutPresenter implements IFragmentPresenter {

    public static final String GAME = "GAME";
    public static final String NEWEST_GAME = "NEWEST_GAME";
    public static final String HOT_GAME = "HOT_GAME";
    public static final String CLASSIES_GAME = "CLASSIES_GAME";

    public GameMainPresenter(BaseActivity baseActivity, BaseFragment baseFragment, TabLayoutFragment filmMainIView) {
        super(baseActivity, baseFragment, filmMainIView);
    }

    @Override
    public BaseFragment newFragment(StripTabItem stripTabItem) {
        switch (stripTabItem.getType()) {
            case GAME:
                return GameFragment.newInstance();
            case NEWEST_GAME:
                return NewestGameFragment.newInstance();
            case HOT_GAME:
                return HotGameFragment.newInstance();
            case CLASSIES_GAME:
                return ClassicGameFragment.newInstance();
        }
        return null;
    }

    @Override
    public ArrayList<StripTabItem> generateTabs() {
        ArrayList<StripTabItem> items = new ArrayList<>();
        items.add(new StripTabItem(GAME, "游戏"));
        items.add(new StripTabItem(NEWEST_GAME, "最新单机"));
        items.add(new StripTabItem(HOT_GAME, "热门单机"));
        items.add(new StripTabItem(CLASSIES_GAME, "经典单机"));
        return items;
    }
}
