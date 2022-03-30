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
@Service
public class ActivityServiceImpl implements ActivityService {



    @Autowired
    private ActivityMapper activityMapper;


    @Override
    public int saveCreateActivity(Activity activity) {
        int i = activityMapper.insertSelective(activity);
        return i;
    }

    @Override
    public List<Activity> queryActivityByConditionForPage(Map<String, Object> map) {
        return activityMapper.selectActivityByConditionForPage(map);
    }

    @Override
    public int queryCountOfActivityByCondition(Map<String, Object> map) {
        return activityMapper.selectCountOfActivityByContidion(map);
    }

    @Override
    public int deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    @Override
    public Activity selectActivityById(String id) {
        return activityMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateActivity(Activity example) {
        return activityMapper.updateByPrimaryKeySelective(example);
    }

    @Override
    public List<Activity> queryActivityAll() {
        return activityMapper.queryActivityAll();
    }

    @Override
    public List<Activity> queryActivityById(String[] ids) {
        return activityMapper.queryActivityById(ids);
    }

    @Override
    public int saveCreateActivityById(List<Activity> activities) {
        return activityMapper.insertCreateActivityById(activities);
    }

    @Override
    public Activity queryActivityForDetailById(String id) {
        return activityMapper.selectActivityForDetailById(id);
    }
}
