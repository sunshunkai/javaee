package com.ssk.interceptor;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/4/15
 */
//@Component
public class MyIntercepter01 implements HandlerInterceptor {
    /**
     * 会在handler⽅法业务逻辑执⾏之前执⾏
     * 往往在这⾥完成权限校验⼯作
     * @param request
     * @param response
     * @param handler
     * @return 返回值boolean代表是否放⾏，true代表放⾏，false代表中⽌
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse
            response, Object handler) throws Exception {
        System.out.println("MyIntercepter01 preHandle......");
        return true;
    }
    /**
     * 会在handler⽅法业务逻辑执⾏之后尚未跳转⻚⾯时执⾏
     * @param request
     * @param response
     * @param handler
     * @param modelAndView 封装了视图和数据，此时尚未跳转⻚⾯呢，你可以在这⾥针对返回的
    数据和视图信息进⾏修改
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse
            response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("MyIntercepter01 postHandle......");
    }
    /**
     * ⻚⾯已经跳转渲染完毕之后执⾏
     * @param request
     * @param response
     * @param handler
     * @param ex 可以在这⾥捕获异常
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("MyIntercepter01 afterCompletion......");
    }
}
