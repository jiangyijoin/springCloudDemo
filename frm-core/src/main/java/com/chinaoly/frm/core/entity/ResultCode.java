package com.chinaoly.frm.core.entity;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
    /**成功**/
    SUCCESS(200),
    /**失败**/
    FAIL(400),
    /**未认证（签名错误）**/
    UNAUTHORIZED(401),
    /**接口不存在**/
    NOT_FOUND(404),
    /**服务器内部错误**/
    INTERNAL_SERVER_ERROR(500),
    /**重定向至登录界面**/
    REDIRECT_LOGIN(10999),
    /**提示至登录界面**/
    REST_LOGIN(10989),
    /**REST退出**/
    REST_LOGOUT(10988),
    /**提示权限不足**/
    PROMPT_AUTH(10998),
    /**系统级别错误**/
    ERROR(10997),
    /**没有参数**/
    NO_PARAM(10901),
    /**空**/
    NULL(10902),
    /**没有此页**/
    NO_PAGE(10905),
    /**执行成功**/
    DO_OK(10906),
    /**执行失败**/
    DO_FAIL(10907),
    /**验证成功**/
    CHECK_OK(10996),
    /**验证失败**/
    CHECK_FAIL(10995),
    /**没有查询结果**/
    NO_RECORD(10908),
    /**没有EXPLAIN注解**/
    NO_EXPLAIN(10909),
    /**JPA HANDLER 通配符转换失败**/
    WBOOST_HANDLER_BASEPACKAGE_RESOLVE_ERROR(10910),
    /**连接接口失败**/
    CONNECTION_ERROR(10911),
    /**分页转换解析失败**/
    PAGE_RESOLVER_ERROR(10912),
    /**返回原样数据**/
    PROMPT(10913),
    /**解析失败**/
    PARSE_ERROR(10914),
    /**无含义**/
    NOTHING(0);

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
