package com.chinaoly.frm.login.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JwtHelper {


    // 过期时间5分钟
    private static final long EXPIRE_TIME = 5*60*1000;

    /**
     * 解析jwt
     */
//    public static Claims parseJWT(String jsonWebToken, String base64Security){
//
//    }

    /**
     * 构建jwt
     */
    public static String createJWT(String name, String password
    		/*String userId, String role, String audience, String issuer,  String base64Security*/)
    {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        //byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(password);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
        		.claim("user", name)
        		.claim("password", password)
        		/*.claim("role", role)
                .claim("userid", userId)
                .setIssuer(issuer)
                .setAudience(audience)*/
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        long expMillis = nowMillis + EXPIRE_TIME;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp).setNotBefore(now);

        //生成JWT
        return builder.compact();
    }



    public static void main(String[] args) throws Exception {
        JwtHelper jwt = new JwtHelper();
        String token = jwt.createJWT("jiangyi","23333");
        System.out.println(token);
    }

}
