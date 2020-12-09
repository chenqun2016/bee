package com.bee.user.ui.base.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * 创建人：进京赶考
 * 创建时间：2020/12/09  21：13
 * 描述：
 */
public abstract class BaseViewPagerAdapter extends FragmentStatePagerAdapter {
    public BaseViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }
}
