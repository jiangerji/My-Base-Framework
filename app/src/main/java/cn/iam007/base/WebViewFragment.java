package cn.iam007.kepler4520;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.iam007.pic.clean.master.base.BaseFragment;

/**
 * Created by Administrator on 2015/6/5.
 */
public class WebViewFragment extends BaseFragment {

    public final static String DATA_URL = "DATA_URL";
    public final static String DATA_TITLE = "DATA_TITLE";

    private WebView mWebView = null;
    private View mLoadingProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.iam007_activity_webview, null);
        init(rootView);
        return rootView;
    }

    void init(View rootView) {
        mWebView = (WebView) rootView.findViewById(cn.iam007.base.R.id.webview);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (URLUtil.isNetworkUrl(url)) {
                    if (mLoadingProgressBar != null) {
                        mLoadingProgressBar.setVisibility(View.VISIBLE);
                        mLoadingProgressBar.startAnimation(
                                AnimationUtils.loadAnimation(getActivity(),
                                        R.anim.abc_fade_in));
                    }
                    view.loadUrl(url);
                } else {
                    // Otherwise allow the OS to handle things like tel, mailto, etc.
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (mLoadingProgressBar != null) {
                    mLoadingProgressBar.setVisibility(View.GONE);
                    mLoadingProgressBar.startAnimation(
                            AnimationUtils.loadAnimation(getActivity(),
                                    R.anim.abc_fade_out));
                }
            }
        });

        mLoadingProgressBar = getToolbar().findViewById(R.id.toolbar_progress_bar);

        Bundle bundle = getArguments();

        if (bundle != null) {
            String url = bundle.getString(DATA_URL);
            if (URLUtil.isNetworkUrl(url)) {
                mWebView.loadUrl(url);
                mLoadingProgressBar.setVisibility(View.VISIBLE);
            }

            String title = bundle.getString(DATA_TITLE);
            if (!TextUtils.isEmpty(title)) {
                getActivity().setTitle(title);
            }
        }


    }

    /**
     * @return 是否已经处理返回按钮事件
     */
    public boolean onBackPressed() {
        boolean result = false;
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            result = true;
        }
        return result;
    }
}
