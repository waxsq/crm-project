package crm.workbench.web.contoller;

import crm.settings.entity.User;
import crm.tools.Constant;
import crm.tools.ReturnObject;
import crm.tools.Utils;
import crm.workbench.web.entity.ActivityRemark;
import crm.workbench.web.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: waxsq
 * @date:
 */
@Controller
public class ActivityRemarkController {

    @Autowired
    private ActivityRemarkService activityRemarkService;


    @RequestMapping("/workbench/activity/saveActivityRemarkForDetail.do")
    @ResponseBody
    public Object saveActivityRemarkForDetail(ActivityRemark activityRemark, HttpSession httpSession)
    {
        User o = (User)httpSession.getAttribute(Constant.SESSION_USER.getCode());
        ReturnObject returnObject = new ReturnObject();
        activityRemark.setId(Utils.getUUID());
        activityRemark.setCreateTime(Utils.getTime());
        activityRemark.setCreateBy(o.getId());
        activityRemark.setEditFlag("0");
        try {
            int i = activityRemarkService.saveActivityRemarkForDetail(activityRemark);
            if (i<=0)
            {
                returnObject.setCode("0");
                returnObject.setMessge("保存失败");
            } else {
                returnObject.setCode("1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode("0");
            returnObject.setMessge("保存失败");
        }
        return returnObject;
    }
}
