package com.example.fastkit.utils.share;


import android.widget.TextView;

public class StringUtil {
    public static final String URL_PREFIX = "http://";

    public static final String URL_PREFIXs = "https://";
    /**
     * 判断字符是否非空
     *
     * @param s
     * @param trim
     * @return
     */
    public static boolean isNotEmpty(String s, boolean trim) {
        if (s == null) {
            return false;
        }
        if (trim) {
            s = s.trim();
        }
        if (s.length() <= 0) {
            return false;
        }
        return true;
    }

    /**获取网址，自动补全
     * @param tv
     * @return
     */
    public static String getCorrectUrl(TextView tv) {
        return getCorrectUrl(getString(tv));
    }


    /**获取去掉前后空格后的string,为null则返回""
     * @param tv
     * @return
     */
    public static String getTrimedString(TextView tv) {
        return getTrimedString(getString(tv));
    }

    public static String getCorrectUrl(String url) {

        if (isNotEmpty(url, true) == false) {
            return "";
        }

        if (isUrl(url) == false) {
            return URL_PREFIX + url;
        }
        return url;
    }
    public static String getString(TextView tv) {
        if (tv == null || tv.getText() == null) {
            return "";
        }
        return getString(tv.getText().toString());
    }

    public static String getString(String s) {
        return s == null ? "" : s;
    }

    public static boolean isUrl(String url) {
        if (isNotEmpty(url, true) == false) {
            return false;
        } else if (! url.startsWith(URL_PREFIX) && ! url.startsWith(URL_PREFIXs)) {
            return false;
        }

        return true;
    }

    public static String getTrimedString(String s) {
        return getString(s).trim();
    }
}
