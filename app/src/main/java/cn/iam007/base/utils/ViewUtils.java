package cn.iam007.base.utils;

import android.graphics.Point;
import android.view.View;

/**
 * Created by Administrator on 2015/7/7.
 */
public class ViewUtils {

    public static Point translateLocationWithOther(View view, View target) {
        Point location = new Point();

        int[] viewLocation = new int[2];
        int[] targetLocation = new int[2];
        view.getLocationInWindow(viewLocation);
        target.getLocationInWindow(targetLocation);
        location.set(viewLocation[0] - targetLocation[0], viewLocation[1] - targetLocation[1]);

        return location;
    }
}
