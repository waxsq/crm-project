package crm.workbench.web.service;

import crm.workbench.web.entity.ActivityRemark;
import crm.workbench.web.mapper.ActivityRemarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: waxsq
 * @date:
 */
@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;


    /**
     * 返回评论的所有信息
     *
     * @param id
     */
    @Override
    public List<ActivityRemark> queryRemarkForDetailByActivityId(String id) {
        return activityRemarkMapper.selectActivityRemarkForDetailByActivityId(id);
    }



    /**
     * 保存评论
     *
     * @param activityRemark
     */
    @Override
    public int saveActivityRemarkForDetail(ActivityRemark activityRemark) {
        return activityRemarkMapper.insertSelective(activityRemark);
    }
}
