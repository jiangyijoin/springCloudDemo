package com.chinaoly.frm.login.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.ByteSource;

import javax.naming.AuthenticationException;

public class AuthenticationInfo {
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //1.把AuthenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        //2.从UsernamePasswordToken中获取userCode
        String userCode = usernamePasswordToken.getUsername();
        String userInfoKey = "aum:user:" + userCode;
//        UserEntity userEntity;
        //3.获取用户信息userEntity
        //3.1 从redis中获取
//        String strUserInfo;
//        try {
//            strUserInfo = JedisCacheUtil.get(userInfoKey);
//            if (!StringUtils.isBlank(strUserInfo)) {
//                userEntity = JacksonJsonUntil.jsonToPojo(strUserInfo, UserEntity.class);
//            } else {
//                userEntity = addUserAndGetUser(userCode, userInfoKey);
//            }
//        } catch (Exception e) {
//            userEntity = addUserAndGetUser(userCode, userInfoKey);
//        }
        //6.根据用户的情况，来构建AuthenticationInfo对象并返回
       // String credentials = userEntity.getPassword();
        //使用ByteSource.Util.bytes()来计算盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(userCode);

       // return new SimpleAuthenticationInfo(userEntity, credentials, credentialsSalt, getName());
        return null;
    }
}
