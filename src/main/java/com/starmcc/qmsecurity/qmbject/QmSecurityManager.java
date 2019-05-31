package com.starmcc.qmsecurity.qmbject;


import com.starmcc.qmsecurity.config.QmSecurityContent;
import com.starmcc.qmsecurity.entity.QmUserInfo;
import com.starmcc.qmsecurity.exception.QmSecurityCreateTokenException;
import com.starmcc.qmsecurity.exception.QmSecurityQmUserInfoException;
import com.starmcc.qmsecurity.utils.QmSecurityTokenTools;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author qm
 * @date 2018/12/22 16:59
 * @Description QmSecurity核心工具类
 */
public class QmSecurityManager implements Qmbject {

    /**
     * rquest对象
     */
    private HttpServletRequest request;
    /**
     * response对象
     */
    private HttpServletResponse response;

    /**
     * 注入相关Spring依赖
     */
    private QmSecurityManager() {
        if (request == null) {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }
        if (response == null) {
            response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        }
    }

    /**
     * 获取QmBject实例
     *
     * @return
     */
    public static Qmbject getQmbject() {
        return new QmSecurityManager();
    }


    @Override
    public String login(QmUserInfo qmUserInfo)
            throws QmSecurityQmUserInfoException, QmSecurityCreateTokenException {
        if (!verifyQmUserInfo(qmUserInfo)) {
            throw new QmSecurityQmUserInfoException();
        }
        // 创建token
        qmUserInfo.setSignTime(new Date());
        return QmSecurityTokenTools.createToken(qmUserInfo);
    }


    @Override
    public QmUserInfo getUserInfo() {
        try {
            return (QmUserInfo) request.getAttribute(QmUserInfo.class.getName());
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public List<String> extractMatchingURI() {
        QmUserInfo qmUserInfo = this.getUserInfo();
        return QmSecurityContent.getRealm().authorizationMatchingURI(qmUserInfo);
    }


    /**
     * 校验登录对象是否完整
     *
     * @param qmUserInfo
     * @return
     */
    private boolean verifyQmUserInfo(QmUserInfo qmUserInfo) {
        if (qmUserInfo == null) {
            return false;
        }
        if ("".equals(qmUserInfo.getIdentify())) {
            return false;
        }
        if (qmUserInfo.getUser() == null) {
            return false;
        }
        return true;
    }


}
