package com.starmcc.qmsecurity.interceptor;


import com.starmcc.qmsecurity.basic.QmSecurityBasic;
import com.starmcc.qmsecurity.basic.QmSecurityBasicImplementation;
import com.starmcc.qmsecurity.note.QmAuthPass;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

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
        // 判断是否为控制器方法
        if (!(handler instanceof HandlerMethod)) {
            // 如果不是则对象为 ResourceHttpRequestHandler MVC 方法跳过校验
            return false;
        }
        // 获取方法对象
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 调用getAnnotation获取方法接口标注的QmPass注解的实例。
        QmAuthPass pass = method.getAnnotation(QmAuthPass.class);
        // 定义是否需要授权匹配，默认为true。当标注了@QmAuthPass时，则该值会变为false;
        return qmSecurityBasic.securityCheck(request, response, Objects.isNull(pass));
    }
}
