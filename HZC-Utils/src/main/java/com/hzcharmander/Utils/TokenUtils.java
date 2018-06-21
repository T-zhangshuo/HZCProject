package com.hzcharmander.Utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token 类
 */
public class TokenUtils {
    private static final String SECRET = "WXRPOd##123?!@23abcd!";
    private static final String EXP = "exp";
    private static final String PAYLOAD = "payload";

    /**
     * 生成Token:jwt
     * @param object 传入的加密对象 - 放入PAYLOAD
     * @param maxAge 过期事件,单位毫秒
     * @param <T>
     * @return
     */
    public static <T> String sign(T object, long maxAge) {
        Map<String, Object> map = new HashMap<String, Object>();
        String jsonString = JSON.toJSONString(object);
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        long exp = System.currentTimeMillis() + maxAge;
        String token = null;
        try {
            token = JWT.create()
                    .withHeader(map)//header
                    .withClaim(PAYLOAD, jsonString)//存放的内容 json
                    .withClaim(EXP, new DateTime(exp).toDate())//超时时间
                    .sign(Algorithm.HMAC256(SECRET));//密钥
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 解密token
     * @param token jwt类型的token
     * @param classT 加密时的类型
     * @param <T>
     * @return 返回解密后的对象 - 如果token过期返回空对象
     */
    public static <T> T unsign(String token, Class<T> classT)  {
        if(StringUtils.isEmpty(token)) return null;
        DecodedJWT decode = JWT.decode(token);
        Map<String, Claim> claims = decode.getClaims();
        if (claims.containsKey(EXP) && claims.containsKey(PAYLOAD)){
            long tokenTime = claims.get(EXP).asDate().getTime();
            long nowTime = new Date().getTime();
            // 判断令牌是否超时
            if (tokenTime > nowTime){
                String json = claims.get(PAYLOAD).asString();
                return JSON.parseObject(json, classT);
            }
        }
        return null;
    }
}
