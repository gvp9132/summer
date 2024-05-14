package org.gvp.common;

import java.util.UUID;

/**
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 构建一个32位的uuid,不包含"-"
     */
    public static String uuid32(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
