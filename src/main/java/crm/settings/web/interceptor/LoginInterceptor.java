package crm.settings.web.interceptor;

import crm.settings.entity.User;
import crm.tools.Constant;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: waxsq
 * @date:
 */
public class LoginInterceptor implements HandlerInterceptor {
    //到达目标资源之前
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //如果用户没有登录成功，跳转到登录页面
        HttpSession session = httpServletRequest.getSession();
        User user = (User)session.getAttribute(Constant.SESSION_USER.getCode());

        if (user == null)
        {
//            跳转登录页面,一定要带上项目名称，不同在控制器上写(因为SpringMVC帮我们处理好)
//            httpServletResponse.sendRedirect("/crm");
//            不能写死项目
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
            return false;
        }
        System.out.println("*************************");
        System.out.println("通过");
        System.out.println("*************************");
        return true;
    }
    //到达目标资源之后
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
//页面渲染之前
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
