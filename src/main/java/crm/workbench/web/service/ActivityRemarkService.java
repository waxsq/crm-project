package crm.workbench.web.service;

import crm.workbench.web.entity.ActivityRemark;

import java.util.List;

/**
 * @description:
 * @author: waxsq
 * @date:
 */
public interface ActivityRemarkService {

    /**
     * 返回评论的所有信息
     */

    List<ActivityRemark> queryRemarkForDetailByActivityId(String id);

    /**
     * 保存评论
     */
    int saveActivityRemarkForDetail(ActivityRemark activityRemark);

}
