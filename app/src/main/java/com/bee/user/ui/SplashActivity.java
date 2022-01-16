package com.bee.user.ui;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amap.api.maps.MapsInitializer;
import com.amap.api.services.core.ServiceSettings;
import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/17  16：42
 * 描述：
 */
public class SplashActivity extends BaseActivity {
    public final static int REQUEST_CODE_ASK_PERMISSIONS = 100;


        @Override
    protected void initImmersionBar() {
         ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews() {
        showPrivacy();
    }

    private void showPrivacy() {
        //高德地图隐私
        MapsInitializer.updatePrivacyShow(this,true,true);
        MapsInitializer.updatePrivacyAgree(this,true);
        ServiceSettings.updatePrivacyShow(this,true,true);
        ServiceSettings.updatePrivacyAgree(this,true);

        getPermissions();
    }

    private void getPermissions() {
        boolean isallPermission=true;
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = new String[]{
//                    Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
            };
            for (String permission : permissions) {
                int hasPermission = ContextCompat.checkSelfPermission(this, permission);
                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    isallPermission=false;
                    requestPermissions(permissions, REQUEST_CODE_ASK_PERMISSIONS);
                    return;
                }
            }
        }

        if(isallPermission) {
            initData();
        }
    }

    private void initData() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    private int  isForbiddeShow=0;

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,
                                            int[] grantResults) {
        if (REQUEST_CODE_ASK_PERMISSIONS == requestCode) {
            boolean hasRefused = false;
            boolean hasStartLocation = false;

            for (int i = 0; i < permissions.length; i++) {
                String curPermission = permissions[i];
                int curGrantResult = grantResults[i];
                Log.d("lucy", "onRequestPermissionsResult: "+curGrantResult);

                if (!hasRefused) {
                    hasRefused = curGrantResult != PackageManager.PERMISSION_GRANTED;
                }

                //返回true 用户点击了禁止获取权限，但没有勾选不再提示
                //返回false 用户点击了禁止获取权限，且勾选不再提示
                if (curGrantResult != PackageManager.PERMISSION_GRANTED&&!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])){
                    isForbiddeShow=1;
                }

                Log.d("lucy", "showPermission: "+ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]));

            }

            if (hasRefused) {
                finish();//TODO
            }else {
                initData();
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            getPermissions();
        }

    }
}
