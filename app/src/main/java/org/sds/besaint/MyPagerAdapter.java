package org.sds.besaint;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LeftFragment();
            case 1:
                return new CenterFragment();
            case 2:
                return new RightFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getResources().getString(R.string.res_tabLeft);
            case 1:
                return mContext.getResources().getString(R.string.res_tabCenter);
            case 2:
                return mContext.getResources().getString(R.string.res_tabRight);
            default:
                return null;
        }
    }
}
