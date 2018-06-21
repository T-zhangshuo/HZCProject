package com.hzcharmander.Contants;

import java.util.Arrays;
import java.util.List;

public class DataContants {

    //TOKEN 认证失效时间
    public static final Long TOKEN_MAXAGE=12*3600*1000l;

    //请求忽略的URL列表
    public static List<String> URL_IGNORES=Arrays.asList("/web/wx/User/login","/web/image/imgUrl");

    //请求头里面的参数
    public static final String ATTRIBUTE_TOKENINF="TOKENINFO";

    //请求操作成功
    public static final int BASEDATA_OPT_CODE_SUC=1;
    //请求操作 -登录失败
    public static final int BASEDATA_OPT_CODE_LOGINFAIL=20001;

}
