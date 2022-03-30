package crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: waxsq
 * @date:
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index()
    {
        return "index";
    }
}
