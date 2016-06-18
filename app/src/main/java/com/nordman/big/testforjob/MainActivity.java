package com.nordman.big.testforjob;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.blunderer.materialdesignlibrary.handlers.ActionBarDefaultHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.ViewPagerHandler;
import com.blunderer.materialdesignlibrary.models.ViewPagerItem;

public class MainActivity extends com.blunderer.materialdesignlibrary.activities.ViewPagerActivity {
    ViewPagerHandler pagerHandler;

    @Override
    public ViewPagerHandler getViewPagerHandler() {
        pagerHandler = new ViewPagerHandler(this)
                .addPage(R.string.title_section1,
                        MainFragment.newInstance("Material Design ViewPager with Tabs"))
                .addPage(R.string.title_section2,
                        FavoritesFragment.newInstance())
                .addPage(R.string.title_section3,
                        SettingsFragment.newInstance());
        return pagerHandler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==1) {
                    ViewPagerItem item = pagerHandler.getViewPagerItems().get(position);
                    ((FavoritesFragment)item.getFragment()).refreshFavorites();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Log.d("LOG","OnCreate()");
    }

    @Override
    public boolean showViewPagerIndicator() {
        return false;
    }

    @Override
    public boolean replaceActionBarTitleByViewPagerPageTitle() {
        return true;
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener userOnPageChangeListener) {
        super.setOnPageChangeListener(userOnPageChangeListener);
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
