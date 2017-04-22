package cn.hudp.androidnote.Permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import cn.hudp.androidnote.MVVM.MvvmBean;
import cn.hudp.androidnote.R;
import cn.hudp.androidnote.databinding.ActivityPermissionBinding;

public class PermissionActivity extends AppCompatActivity {
    ActivityPermissionBinding binding;
    MvvmBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_permission);
        binding.setActivity(this);
    }

    private static final int REQUEST_CODE_READ_PHONE_STATE = 1;

    public void onClick(View v) {
        // 判断是否有权限
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //没有权限就申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_READ_PHONE_STATE);
        } else {
            getIMEI();
        }
    }

    /**
     * 申请权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_PHONE_STATE: {
                //判断是否获取了权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getIMEI();
                } else {
                    Toast.makeText(this, "请授予权限", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void getIMEI() {
        try {
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                String imei = tm.getDeviceId();
                bean = new MvvmBean("IMEI:" + imei, "");
                binding.setBean(bean);
            }
        } catch (Exception e) {
            bean = new MvvmBean("IMEI  获取失败", "");
            binding.setBean(bean);
        }
    }
}
