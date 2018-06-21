package com.hzcharmander.listener;

import com.hzcharmander.Contants.DataContants;
import com.hzcharmander.Entity.TokenInfo;
import com.hzcharmander.Utils.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        //如果是登录接口的情况下，忽略token
//        String requestUrl = httpServletRequest.getRequestURI();
//        System.out.println(requestUrl);
//        if (DataContants.URL_IGNORES.indexOf(requestUrl) != -1) {
//            //这个里面的请求都是忽略的
//            return true;
//        } else {
//            //验证登录接口
//            String authorization = httpServletRequest.getHeader("Authorization");
//            TokenInfo tokenInfo = TokenUtils.unsign(authorization, TokenInfo.class);
//            if (tokenInfo == null) {
//                return false;
//            }
//            else {
//                //放入到请求头里面
//                httpServletRequest.setAttribute(DataContants.ATTRIBUTE_TOKENINF, tokenInfo);
//                return true;
//            }
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
