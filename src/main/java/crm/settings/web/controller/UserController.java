package crm.settings.web.controller;

import crm.settings.entity.User;
import crm.settings.service.UserService;
import crm.tools.Constant;
import crm.tools.ReturnObject;
import crm.tools.Utils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: waxsq
 * @date:
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * url要和controller方法处理完请求之后，响应信息返回的页面的资源目录保持一致
     */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        //请求转发到登录页面
        return "settings/qx/user/login";
    }


    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        //调用service层方法，查询用户
        User user=userService.queryUserByActAndPwd(map);

        //根据查询结果，生成响应信息
        ReturnObject returnObject=new ReturnObject();
        if(user==null){
            //登录失败,用户名或者密码错误
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL.getCode());
            returnObject.setMessge("用户名或者密码错误");
        }else{//进一步判断账号是否合法
            //user.getExpireTime()   //2019-10-20
            //        new Date()     //2020-09-10
            if(Utils.Time(user.getExpireTime())){
                //登录失败，账号已过期
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL.getCode());
                returnObject.setMessge("账号已过期");
            }else if("0".equals(user.getLockState())){
                //登录失败，状态被锁定
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL.getCode());
                returnObject.setMessge("状态被锁定");
            }else if(!user.getAllowIps().contains(request.getRemoteAddr())){
                //登录失败，ip受限
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL.getCode());
                returnObject.setMessge("ip受限");
            }else{
                //登录成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS.getCode());

//                //把user保存到session中
                session.setAttribute(Constant.SESSION_USER.getCode(),user);

//                如果需要记住密码，则往外写cookie,记住上次输入的用户名和密码
                if("true".equals(isRemPwd)){
                    Cookie c1=new Cookie("loginAct",user.getLoginAct());
                    c1.setMaxAge(10*24*60*60);
                    response.addCookie(c1);
                    Cookie c2=new Cookie("loginPwd",user.getLoginPwd());
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c2);
                }else{
                    //把没有过期cookie删除
                    Cookie c1=new Cookie("loginAct","1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2=new Cookie("loginPwd","1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }

        return returnObject;
    }


    /**
     * 退出登录
     * 1、清空cookies值
     * 2、清空sesion值
     */

    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response, HttpSession session)
    {
//        覆盖cookies，并设置生存时间
        Cookie c1=new Cookie("loginAct","1");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2=new Cookie("loginPwd","1");
        c2.setMaxAge(0);
        response.addCookie(c2);
//        清空session
        session.invalidate();
//        返回主页，主页会自动跳转到登陆页面,重定向，使状态栏变登录链接
        return "redirect:/";
    }

}
