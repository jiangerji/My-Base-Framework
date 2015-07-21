package cn.iam007.base.utils;

import java.io.File;

/**
 * Created by Administrator on 2015/7/8.
 */
public class FileUtils {

    /**
     * 删除指定的文件或者目录
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file != null) {
            if (file.isFile()) {
                file.delete();
            } else {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File item : files) {
                        deleteFile(item);
                    }
                }

                file.delete();
            }
        }
    }
}
