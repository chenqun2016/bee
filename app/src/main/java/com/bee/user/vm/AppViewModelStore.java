package com.bee.user.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

/**
 * 创建时间：2021/10/16
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class AppViewModelStore extends AndroidViewModel {
    public AppViewModelStore(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<Integer> chartData = new MutableLiveData<Integer>();
}
