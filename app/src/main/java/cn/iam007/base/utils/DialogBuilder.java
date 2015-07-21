package cn.iam007.base.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import cn.iam007.base.R;

/**
 * 用于配置创建MaterialDialog
 */
public class DialogBuilder extends MaterialDialog.Builder {

    public DialogBuilder(Context context) {
        super(context);

        theme(Theme.LIGHT);

        // 设置字体颜色
        //        new MaterialDialog.Builder(this)
        //        .titleColorRes(R.color.material_red_500)
        //        .contentColor(Color.WHITE) // notice no 'res' postfix for literal color
        //        .dividerColorRes(R.color.material_pink_500)
        //        .backgroundColorRes(R.color.material_blue_grey_800)
        //        .positiveColorRes(R.color.material_red_500)
        //        .neutralColorRes(R.color.material_red_500)
        //        .negativeColorRes(R.color.material_red_500)
        //        .widgetColorRes(R.color.material_red_500)
        //        .show();

        // 设置字体颜色
        titleColorRes(R.color.iam007_dialog_title);
        dividerColorRes(R.color.iam007_divider);
        positiveColorRes(R.color.iam007_dialog_positive);
        negativeColorRes(R.color.iam007_dialog_negative);
        backgroundColorRes(R.color.iam007_dialog_background);
    }

    @Override
    public MaterialDialog build() {
        MaterialDialog dialog = super.build();
        PlatformUtils.applyFonts(getContext(), dialog.getView());
        return dialog;
    }

    @Override
    public MaterialDialog show() {
        MaterialDialog dialog = super.show();
        PlatformUtils.applyFonts(getContext(), dialog.getView());
        return dialog;
    }
}
