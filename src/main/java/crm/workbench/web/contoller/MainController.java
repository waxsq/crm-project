package crm.workbench.web.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: waxsq
 * @date:
 */
@Controller
public class MainController {

    @RequestMapping("/workbench/main/index.do")
    public String index()
    {
//        跳转页面
        return "workbench/main/index";
    }
}
