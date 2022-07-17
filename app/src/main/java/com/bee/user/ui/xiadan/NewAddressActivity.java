package com.bee.user.ui.xiadan;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.bee.user.R;
import com.bee.user.bean.AddressBean;
import com.bee.user.bean.DingWeiBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.nearby.DingWeiActivity;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.RadioGroupPlus;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function4;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.bee.user.ui.nearby.DingWeiActivity.REQUEST_CODE_LOCATION_ACTIVITY;
import static com.bee.user.ui.nearby.DingWeiActivity.RESULT_CODE_LOCATION_ACTIVITY;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/17  20：39
 * 描述：
 */
public class NewAddressActivity extends BaseActivity {
    public static final int REQUEST_CODE_OLD = 5;
    public static final int REQUEST_CODE_NEW = 7;
    public static final int RESULT_CODE_NEWADDRESS = 6;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;


    @BindView(R.id.tv_right)
    TextView tv_right;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.rgp_sex)
    RadioGroupPlus rgp_sex;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_dizhi_text)
    TextView tv_dizhi_text;
    @BindView(R.id.tv_menpai_text)
    TextView tv_menpai_text;
    @BindView(R.id.rgp_tags)
    RadioGroupPlus rgp_tags;
    @BindView(R.id.tv_sure)
    TextView tv_sure;

    AddressBean address;

    @OnClick({R.id.tv_sure,R.id.tv_right,R.id.tv_dizhi_text})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_sure:
                saveAddress();
                break;
            case R.id.tv_right:
                deleteAddress();
                break;
            case R.id.tv_dizhi_text:
                Intent intent = new Intent(this, DingWeiActivity.class);
                if(null != address){
                    intent.putExtra("longitude",address.longitude+"");
                    intent.putExtra("latitude",address.latitude+"");
                }else{
                    AMapLocation location = SPUtils.geTinstance().getLocation();
                    if(null != location){
                        intent.putExtra("longitude",location.getLongitude()+"");
                        intent.putExtra("latitude",location.getLatitude()+"");
                    }
                }

                startActivityForResult(intent,REQUEST_CODE_LOCATION_ACTIVITY);
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_LOCATION_ACTIVITY && resultCode == RESULT_CODE_LOCATION_ACTIVITY && null != data){
            DingWeiBean data1 = (DingWeiBean) data.getSerializableExtra("data");
            if(null != data1){
                if(null == address){
                    address = new AddressBean();
                }
                address.detailAddress = data1.name;
                address.latitude = data1.latitude;
                address.longitude = data1.longitude;
                address.postCode = data1.cityCode;
                tv_dizhi_text.setText(data1.name);
            }
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        status_bar.setBackgroundResource(R.drawable.btn_gradient_yellow);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_address;
    }

    @Override
    public void initViews() {
        toolbar_title.setText("新增地址");
        address  = (AddressBean) getIntent().getSerializableExtra("address");

        if(null != address){
            tv_right.setText("删除");
            tv_right.setVisibility(View.VISIBLE);
        }

        InitialValueObservable<CharSequence> c1 = RxTextView.textChanges(tv_name);
        InitialValueObservable<CharSequence> c2 = RxTextView.textChanges(tv_phone);
        InitialValueObservable<CharSequence> c3 = RxTextView.textChanges(tv_dizhi_text);
        InitialValueObservable<CharSequence> c4 = RxTextView.textChanges(tv_menpai_text);
        rgp_tags.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroupPlus group, int checkedId) {
                checkStatus();
            }
        });
        rgp_sex.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroupPlus group, int checkedId) {
                checkStatus();
            }
        });

        Observable.combineLatest(c1, c2,c3,c4, ( Function4<CharSequence, CharSequence,CharSequence, CharSequence, Boolean>) (charSequence, charSequence2,charSequence3, charSequence4) -> {

            return !TextUtils.isEmpty(charSequence) &&
                    !TextUtils.isEmpty(charSequence2) &&
                    !TextUtils.isEmpty(charSequence3) &&
                    !TextUtils.isEmpty(charSequence4) &&
                    rgp_tags.getCheckedRadioButtonId() != -1 &&
                    rgp_sex.getCheckedRadioButtonId() != -1;

        }).subscribe(new Observer<Boolean>() {

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {

                setButtonStatus(aBoolean);
            }
        });
        setDatas();

    }

    private void setDatas() {
        if(null != address){
            tv_name.setText(address.name+"");
            tv_phone.setText(address.phoneNumber+"");
            tv_dizhi_text.setText(address.detailAddress+"");
            tv_menpai_text.setText(address.houseNumber+"");

            rgp_sex.check(address.gender==2?R.id.rb_1:R.id.rb_2);
            switch (address.tag){
                case 1:
                    rgp_tags.check(R.id.rb_3);
                    break;
                case 2:
                    rgp_tags.check(R.id.rb_4);
                    break;
                case 3:
                    rgp_tags.check(R.id.rb_5);
                    break;
            }
        }

        AMapLocation location = SPUtils.geTinstance().getLocation();
        if(null != location){
            tv_dizhi_text.setText(location.getPoiName());
        }
    }




    private void checkStatus() {
        setButtonStatus(!TextUtils.isEmpty(tv_name.getText().toString()) &&
                !TextUtils.isEmpty(tv_phone.getText().toString()) &&
                !TextUtils.isEmpty(tv_dizhi_text.getText().toString()) &&
                !TextUtils.isEmpty(tv_menpai_text.getText().toString()) &&
                rgp_tags.getCheckedRadioButtonId() != -1 &&
                rgp_sex.getCheckedRadioButtonId() != -1);

    }

    private void setButtonStatus(Boolean aBoolean) {
        if (aBoolean) {
            tv_sure.setEnabled(true);
            tv_sure.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
        } else {
            tv_sure.setEnabled(false);
            tv_sure.setBackgroundResource(R.drawable.btn_gradient_grey_round);
        }
    }



    private void saveAddress() {
        Map<String, String> map = new HashMap<>();
        map.put("name", tv_name.getText()+"");
        map.put("phoneNumber", tv_phone.getText()+"");
        map.put("detailAddress", tv_dizhi_text.getText()+"");
        map.put("houseNumber", tv_menpai_text.getText()+"");
        map.put("gender", (rgp_sex.getCheckedRadioButtonId()==R.id.rb_1?2:1)+"");

        int tag;
        switch (rgp_tags.getCheckedRadioButtonId()){
            case R.id.rb_3:
                tag = 1;
                break;
            case R.id.rb_4:
                tag = 2;
                break;
            case R.id.rb_5:
                tag = 3;
                break;
            default:
                tag = 1;
                break;
        }
        map.put("tag", tag+"");
        if(null != address){
            map.put("id", address.id+"");
            map.put("memberId", address.memberId+"");
            map.put("defaultStatus", address.defaultStatus+"");
            map.put("postCode", address.postCode+"");
            map.put("province", address.province+"");
            map.put("city", address.city+"");
            map.put("district",address.district+"");
            map.put("latitude", address.latitude+"");
            map.put("longitude", address.longitude+"");

            address.name = tv_name.getText()+"";
            address.phoneNumber = tv_phone.getText()+"";
            address.detailAddress =tv_dizhi_text.getText()+"";
            address.houseNumber = tv_menpai_text.getText()+"";
            address.gender = (rgp_sex.getCheckedRadioButtonId()==R.id.rb_1?2:1);
            address.tag = tag;
        }else{
            AMapLocation location = SPUtils.geTinstance().getLocation();

            if(null != location){
                map.put("id", "");
                map.put("memberId", "");
                map.put("defaultStatus", "");
                map.put("postCode", location.getCityCode()+"");
                map.put("province", location.getProvince()+"");
                map.put("city", location.getCity()+"");
                map.put("district",location.getDistrict()+"");
                map.put("latitude", location.getLatitude()+"");
                map.put("longitude", location.getLongitude()+"");
            }
        }


        Api.getClient(HttpRequest.baseUrl_member).saveAddress(Api.getRequestBody(map))
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSuccess(String token) {
                        Intent intent = new Intent();
                        intent.putExtra("address",address);
                        intent.putExtra("clickPosition",getIntent().getIntExtra("clickPosition",0));
                        setResult(RESULT_CODE_NEWADDRESS,intent);
                        finish();
                    }

                    @Override
                    protected void onFail(String errorMsg, int errorCode) {
                        super.onFail(errorMsg, errorCode);
                    }
                });
    }

    private void deleteAddress() {
        Map<String, String> map = new HashMap<>();
        map.put("id",address.id+"");
        Api.getClient(HttpRequest.baseUrl_member).deleteAddress(Api.getRequestBody(map))
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Intent intent = new Intent();
                        intent.putExtra("clickPosition",getIntent().getIntExtra("clickPosition",0));
                        setResult(RESULT_CODE_NEWADDRESS,intent);
                        finish();
                    }
                });
    }
}
