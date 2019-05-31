package com.starmcc.qmsecurity.basic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qm
 * @date 2018/12/23 1:14
 * @Description QmSecurity安全框架底层
 */
public interface QmSecurityBasic {


    /**
     * 拦截器安全校验
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param isPerssions
     * @return
     * @throws Exception
     */
    boolean securityCheck(HttpServletRequest request, HttpServletResponse response, boolean isPerssions) throws Exception;


}
