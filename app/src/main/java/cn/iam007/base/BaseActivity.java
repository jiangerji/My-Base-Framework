package cn.iam007.base;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import cn.iam007.base.utils.DialogBuilder;
import cn.iam007.base.utils.PlatformUtils;

/**
 * 基础类，会使用同一的Google开源Roboto字体，有如下一些其他的特性
 * 1. 使用Toolbar作为ActionBar
 * 2. 子类可以配置显示或隐藏状态栏和工具栏
 * 3. 提供showProgressDialog接口，统一显示加载或等待对话框格式
 * 4. 提供设置应用启动Activity类，在启动Activity需要连按两次才能退出应用,如果没有设置，则会自动的寻找当前应用配置的启动activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    private FrameLayout mContainer = null;
    private Toolbar mToolbar;

    private SystemBarTintManager mTintManager = null;
    private static String mLauncherClass = null;

    /**
     * 设置应用的启动
     *
     * @param componentName
     */
    public static void setLauncherClass(ComponentName componentName) {
        if (componentName != null)
            mLauncherClass = componentName.getClassName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (notDisplayStatusbar()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        super.setContentView(R.layout.iam007_activity_base);

        // 获取启动的activity
        if (mLauncherClass == null) {
            PackageManager packageManager = this.getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(getPackageName());
            ComponentName name = intent.getComponent();
            mLauncherClass = name.getClassName();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        mTintManager = new SystemBarTintManager(this);
        if (notDisplayStatusbar()) {
            mTintManager.setStatusBarTintEnabled(false);
        } else {
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintResource(R.color.primary);
        }
        mTintManager.setNavigationBarTintEnabled(true);
        mTintManager.setNavigationBarTintResource(R.color.primary);


        mContainer = (FrameLayout) findViewById(R.id.container);

        initView();
    }

    protected boolean notDisplayToolbar() {
        return false;
    }

    protected boolean notDisplayStatusbar() {
        return false;
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if (isLaunchActivity()) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            PlatformUtils.applyFonts(this, mToolbar);
        }

        if (notDisplayToolbar()) {
            mToolbar.setVisibility(View.GONE);
        }

        // 设置debug彩蛋
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDebugClickCount == 0) {
                    mDebugPreClickTime = System.currentTimeMillis();
                } else {
                    long time = System.currentTimeMillis();
                    if (time - mDebugPreClickTime > 250) {
                        mDebugClickCount = 0;
                        return;
                    }
                    mDebugPreClickTime = time;
                }

                mDebugClickCount++;
                if (mDebugClickCount >= 10) {
                    showDebugInfo();
                    mDebugClickCount = 0;
                }
            }
        });
    }

    private int mDebugClickCount = 0;
    private long mDebugPreClickTime = 0;

    private void showDebugInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("Flavor:" + PlatformUtils.getMeteDataByKey(this, "leancloud") + "\n");
        sb.append("Version:" + PlatformUtils.getVersionCode(this));

        Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 获取activity的toolbar实例
     *
     * @return
     */
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * 设置状态栏的背景颜色
     *
     * @param color
     */
    public final void setStatusBarTintColor(int color) {
        mTintManager.setStatusBarTintColor(color);
    }

    /**
     * 设置工具栏的背景颜色
     *
     * @param color
     */
    public final void setToolbarBackgroundColor(int color) {
        mToolbar.setBackgroundColor(color);
    }

    /**
     * 设置导航栏背景颜色
     *
     * @param color
     */
    public final void setNavigationBarTintColor(int color) {
        mTintManager.setNavigationBarTintColor(color);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setContentView(int layoutResID) {
        View.inflate(this, layoutResID, mContainer);
        PlatformUtils.applyFonts(this, mContainer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        boolean launch = needLaunchMainActivity();
        if (launch) {
            try {
                Intent intent = new Intent();
                Class launcherClass = ClassLoader.getSystemClassLoader().loadClass(mLauncherClass);
                intent.setClass(this, launcherClass);
                startActivity(intent);
            } catch (Exception e) {

            }
        }
        super.finish();
    }

    /**
     * 是否是启动的activity
     */
    protected boolean isLaunchActivity() {
        return this.getClass().getName().equalsIgnoreCase(mLauncherClass);
    }

    private boolean needLaunchMainActivity() {
        boolean launcher = false;
        try {
            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1000);

            String packageName;
            String className = null;
            ActivityManager.RunningTaskInfo _info = null;
            for (ActivityManager.RunningTaskInfo info : list) {
                packageName = info.topActivity.getPackageName();
                className = info.topActivity.getClassName();
                if (packageName.equalsIgnoreCase(getPackageName())) {
                    _info = info;
                    break;
                }
            }

            if (_info.numActivities == 1) {
                if (!className.equalsIgnoreCase(mLauncherClass)) {
                    launcher = true;
                }

            }
        } catch (Exception e) {

        }

        return launcher;
    }

    private MaterialDialog mProgressDialog = null;

    protected MaterialDialog showProgressDialog() {
        DialogBuilder builder = new DialogBuilder(this);
        builder.progress(true, 100);
        builder.content("加载中...");
        builder.cancelable(false);
        mProgressDialog = builder.show();

        return mProgressDialog;
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {

            }
        }
        mProgressDialog = null;
    }
}