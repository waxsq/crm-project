package crm.workbench.web.service;

import crm.workbench.web.entity.Activity;
import crm.workbench.web.entity.ActivityExample;
import crm.workbench.web.mapper.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: waxsq
 * @date:
 */
public interface ActivityService {
    int saveCreateActivity(Activity activity);
    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);
    int queryCountOfActivityByCondition(Map<String,Object> map);
    int deleteActivityByIds(String[] ids);
    Activity selectActivityById(String id);
    int updateActivity(Activity activity);
    List<Activity> queryActivityAll();
    List<Activity> queryActivityById(String[] ids);
    int saveCreateActivityById(List<Activity> activities);
    Activity queryActivityForDetailById(String id);
}
