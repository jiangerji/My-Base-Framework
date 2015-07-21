package cn.iam007.base;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

import cn.iam007.base.utils.ImageUtils;
import cn.iam007.base.utils.LogUtil;
import cn.iam007.base.utils.PlatformUtils;
import cn.iam007.base.utils.SharedPreferenceUtil;

/**
 * Created by Administrator on 2015/7/3.
 */
public class BaseApplication extends Application {

    private static BaseApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        String appId = PlatformUtils.getMeteDataByKey(this, "leancloud_app_id");
        String appKey = PlatformUtils.getMeteDataByKey(this, "leancloud_app_key");
        AVOSCloud.initialize(this, appId, appKey);
        LogUtil.d("Init LeanCloud");
        LogUtil.d("    App id:" + appId);
        LogUtil.d("    App key:" + appKey);

        // 初始化显示图片配置，初始化image loader
        ImageUtils.init(this);
        SharedPreferenceUtil.init(this);
    }

    public static BaseApplication getApplication() {
        return mApplication;
    }
}
