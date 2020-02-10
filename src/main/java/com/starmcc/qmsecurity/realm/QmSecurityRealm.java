package com.starmcc.qmsecurity.realm;


import com.starmcc.qmsecurity.entity.QmUserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author qm
 * @date 2018/12/22 17:06
 * @Description QmSecurity授权接口
 */
public interface QmSecurityRealm {

    /**
     * 提供给调度者的授权URI接口。
     *
     * @param qmUserInfo 用户对象
     * @return
     */
    public default List<String> authorizationMatchingURI(QmUserInfo qmUserInfo){
        List<String> list = new ArrayList<>();
        list.add("/**");
        return list;
    }


    /**
     * 提供给调度者的检测用户是否合法的接口
     * 当用户每次请求时进入安全监测时会调用该接口。
     * 注意：返回null表示本次检测不通过，框架自动进行拦截。回调noPassCallBack方法type值为5
     *
     * @param qmUserInfo 用户对象
     * @param request    HttpServletRequest
     * @param response   HttpServletResponse
     * @return
     */
    QmUserInfo authorizationUserInfo(QmUserInfo qmUserInfo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 当安全检测不通过时回调该接口
     * 回调该接口最好的处理方式是处理相关业务并推送错误信息。
     *
     * @param type     1=检测不到token拒绝访问 | 2=非法token,token提取失败 | 3=授权验证authorizationUserInfo拦截 | 4=权限不足,拒绝访问 | 5=token失效 | 6=请求路径错误，404
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    public default void noPassCallBack(int type, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.getWriter().print("安全检测不通过!");
    }


    /**
     * 提供给调用者什么时候进行重置token。
     * 默认机制为 当前时间 是否小于 (签发时间 + 失效时长 / 2)
     *
     * @param tokenExpireTime
     * @param signTime
     * @return 返回true表示需要重置，返回false表示不重置。
     */
    public default boolean verifyRestartToken(long tokenExpireTime, long signTime){
        // 如果exp等于0表示该token永久不过期
        if (tokenExpireTime <= 0) {
            return false;
        }
        // 机制为 当前时间 是否小于 (签发时间 + 失效时长 / 2)
        // 生成(签发时间 + 失效时长 / 2)的token过期时间
        Date tokenExp = new Date(signTime + (tokenExpireTime * 1000 / 2));
        if (System.currentTimeMillis() < tokenExp.getTime()) {
            return false;
        }
        return true;
    }

}
