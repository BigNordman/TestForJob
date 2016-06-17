package com.nordman.big.testforjob;

import com.blunderer.materialdesignlibrary.handlers.ActionBarDefaultHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.ViewPagerHandler;

public class MainActivity extends com.blunderer.materialdesignlibrary.activities.ViewPagerWithTabsActivity {


    @Override
    public ViewPagerHandler getViewPagerHandler() {
        return new ViewPagerHandler(this)
                .addPage(R.string.title_section1,
                        MainFragment.newInstance("Material Design ViewPager with Tabs"))
                /*
                .addPage(R.string.title_section2,
                        MainFragment.newInstance("Material Design ViewPager with Tabs"))
                */
                .addPage(R.string.title_section3,
                        SettingsFragment.newInstance());
    }
    @Override
    public boolean expandTabs() {
        return false;
    }

    @Override
    public int defaultViewPagerPageSelectedPosition() {
        return 0;
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarDefaultHandler(this);
    }

}
