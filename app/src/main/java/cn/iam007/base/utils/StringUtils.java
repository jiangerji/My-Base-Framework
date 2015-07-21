package cn.iam007.base.utils;

import java.util.Random;

/**
 * Created by Administrator on 2015/7/8.
 */
public class StringUtils {

    public final static String CHARS = "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM";

    public static String randomString(int count){
        String result = null;
        if (count > 0) {
            StringBuffer sb = new StringBuffer();
            int index = 0;
            int length = CHARS.length();
            Random random = new Random();
            while (index++ < count) {
                sb.append(CHARS.charAt(random.nextInt(length)));
            }

            result = sb.toString();
        }
        return result;
    }

    public static String randomString(){
        return randomString(32);
    }
}
