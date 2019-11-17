package com.starmcc.qmsecurity.interceptor;


import com.starmcc.qmsecurity.basic.QmSecurityBasic;
import com.starmcc.qmsecurity.basic.QmSecurityBasicImplementation;
import com.starmcc.qmsecurity.config.QmSecurityContent;
import com.starmcc.qmsecurity.note.QmPass;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author qm
 * @date 2018/12/22 16:56
 * @Description QmSecurity安全拦截器
 */
public class QmSecurityInterceptor implements HandlerInterceptor {

    /**
     * 单例底层
     */
    private static QmSecurityBasic qmSecurityBasic;

    public QmSecurityInterceptor() {
        if (qmSecurityBasic == null) {
            qmSecurityBasic = new QmSecurityBasicImplementation();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 放过跨域探路请求 如果项目中设置了跨域限制,会被拦截，框架本身放过处理。
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        // 定义是否需要授权匹配，默认为true。当标注了@QmPass且用户给定needLogin为true时，则该值会变为false;
        boolean isPerssions = true;
        // 判断是否为控制器方法
        if (!(handler instanceof HandlerMethod)) {
            // 如果不是则对象为 ResourceHttpRequestHandler
            QmSecurityContent.getRealm().noPassCallBack(6, request, response);
            return false;
        }
        // 获取方法对象
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 调用getAnnotation获取方法接口标注的QmPass注解的实例。
        QmPass pass = method.getAnnotation(QmPass.class);
        // 查找是否存在pass注解，如果存在则放过请求
        // 如果不为空则方法标注了该注解，进而判断其needLogin的值。
        if (pass != null) {
            if (pass.needLogin()) {
                // 当标注了@QmPass且用户给定needLogin为true时，则该值会变为false;
                isPerssions = false;
            } else {
                // 如果为false，则该请求不需要任何校验token和授权的过程，直接返回true;
                return true;
            }
        }
        return qmSecurityBasic.securityCheck(request, response, isPerssions);
    }
}
