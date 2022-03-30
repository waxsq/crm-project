package crm.workbench.web.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: waxsq
 * @date:
 */
@Controller
public class WorkbenchIndexController {
    @RequestMapping("/workbench/index.do")
    public String index()
    {
//        直接跳转业务页面
        System.out.println("跳转");
        return "workbench/index";
    }
}
