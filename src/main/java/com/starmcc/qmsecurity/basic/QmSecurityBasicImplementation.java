package com.starmcc.qmsecurity.basic;


import com.starmcc.qmsecurity.config.QmSecurityContent;
import com.starmcc.qmsecurity.entity.QmUserInfo;
import com.starmcc.qmsecurity.exception.QmSecurityAnalysisTokenException;
import com.starmcc.qmsecurity.utils.QmSecurityTokenTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author qm
 * @date 2019/5/12 0:42
 * @Description QmSecurity安全框架底层实现
 */
public class QmSecurityBasicImplementation implements QmSecurityBasic {

    private static final Logger LOG = LoggerFactory.getLogger(QmSecurityBasicImplementation.class);

    @Override
    public boolean securityCheck(HttpServletRequest request, HttpServletResponse response, boolean isPerssions)
            throws Exception {
        // 从头部获取token字段 如果为空则直接拦截
        String token = request.getHeader(QmSecurityContent.getHeaderTokenKeyName());
        if (token == null || "".equals(token)) {
            LOG.info("※获取token失败,拒绝访问※");
            QmSecurityContent.getRealm().noPassCallBack(1, request, response);
            return false;
        }
        QmUserInfo qmUserInfo = null;
        try {
            LOG.debug("※分析并提取token信息※");
            qmUserInfo = QmSecurityTokenTools.analysisToken(token);
            LOG.info("※已验证为合法token信息※");
        } catch (QmSecurityAnalysisTokenException e) {
            LOG.info("※提取token失败※");
            QmSecurityContent.getRealm().noPassCallBack(2, request, response);
            return false;
        }
        // 提供给调度者授权调用,可修改用户对象信息。如果返回空,则认为授权失败！否则需调度者返回实质上的用户对象。
        LOG.debug("※进入授权验证※");
        QmUserInfo qmUserInfoAuth = QmSecurityContent.getRealm().authorizationUserInfo(qmUserInfo, request, response);
        if (qmUserInfoAuth == null) {
            LOG.info("※授权验证拦截※");
            // 保存到作用域中提供框架调用
            request.setAttribute(QmUserInfo.class.getName(), qmUserInfo);
            QmSecurityContent.getRealm().noPassCallBack(3, request, response);
            return false;
        }
        LOG.info("※通过授权验证※");
        // 判断token是否过期
        long tokenExpireTime = qmUserInfoAuth.getTokenExpireTime() * 1000;
        long signTime = qmUserInfoAuth.getSignTime().getTime();
        if (!QmSecurityTokenTools.verifyExp(tokenExpireTime, signTime)) {
            // ===================token过期==================
            QmSecurityContent.getRealm().noPassCallBack(5, request, response);
            return false;
        } else if (QmSecurityContent.getRealm().verifyRestartToken(tokenExpireTime, signTime)) {
            // reauthorizationIsRequired 是否需要重新授权，如果是则走这里。
            LOG.debug("※尝试重新签发token※");
            QmSecurityTokenTools.restartCreateToken(qmUserInfoAuth, response);
            LOG.info("※重新签发token成功※");
        }
        // 保存到作用域中提供框架调用
        request.setAttribute(QmUserInfo.class.getName(), qmUserInfoAuth);
        // 该判断为如果标注了@QmPass且needLogin为true时，则isPerssions为false，就不会进入授权匹配了。
        if (isPerssions) {
            LOG.debug("※正在进行URI权限验证※");
            // 获取该角色的权限信息
            List<String> matchingUrls = QmSecurityContent.getRealm().authorizationMatchingUri(qmUserInfoAuth);
            // 获取请求路由 校验该角色是否存在匹配当前请求url的匹配规则。
            if (!this.verifyMatchingURI(request.getServletPath(), matchingUrls)) {
                LOG.info("※权限不足,拒绝访问※");
                QmSecurityContent.getRealm().noPassCallBack(4, request, response);
                return false;
            }
            LOG.info("※用户已通过URI权限验证※");
        }
        LOG.info("※※用户已通过安全框架验证※※");
        return true;
    }

    /**
     * Spring提供的模糊路径匹配算法
     * 匹配角色授权URI
     *
     * @param matchingUrl 匹配路径
     * @param requestUrl  请求地址
     * @return
     */
    private boolean verifyMatchingURI(String requestUri, List<String> matchingUris) {
        for (String matchingUri : matchingUris) {
            // Spring提供的模糊路径匹配算法
            PathMatcher matcher = new AntPathMatcher();
            if (matcher.match(matchingUri, requestUri)) {
                return true;
            }
        }
        return false;
    }


}
