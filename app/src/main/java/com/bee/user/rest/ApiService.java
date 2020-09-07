package com.bee.user.rest;

import com.bee.user.bean.UserBean;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * 验证码登陆
     */
    @POST(HttpRequest.login)
    Observable<BaseResult<UserBean>> login(@Query("token") String token);
}
