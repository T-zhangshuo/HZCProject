package com.hzcharmander.Utils;

import org.apache.commons.lang.StringUtils;

public class FileUtils {


    /**
     * 得到文件后缀名
     * @param fileName 文件名称
     * @return
     */
    public static String getFileSuffix(String fileName){
        if(StringUtils.isEmpty(fileName)) return "";
        int index=fileName.lastIndexOf(".");
        return fileName.substring(index);

    }
}
