package com.bee.user.ui.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;


import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;

/**
 * Created by chenqun on 2017/2/21.
 */

public abstract class LazyFragment extends Fragment {
    private boolean isPrepared;
    private boolean isVisible;


    protected Activity mActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity)context;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
//        Log.d("LazyFragment","setUserVisibleHint--"+isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            if(isPrepared){
                onInvisible();
            }
        }
    }

    protected void onInvisible() {

    }

    private void onVisible() {
        lazyLoad();
    }

    private boolean isLoaded = false;

    private void lazyLoad() {
//        Log.d("LazyFragment","isVisible=="+isVisible+"/isPrepared=="+isPrepared+"/isLoaded=="+isLoaded+"/isAdded=="+isAdded());
        if (!isVisible || !isPrepared || isLoaded || !isAdded()) return;
        getDatas();
        isLoaded = true;
    }

    public void setIsLoaded(Boolean b) {
        this.isLoaded = b;
    }

    protected abstract void getDatas();

    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        super.onDetach();
        isLoaded = false;
        isPrepared = false;
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
