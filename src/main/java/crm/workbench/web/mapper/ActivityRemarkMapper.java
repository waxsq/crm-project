package crm.workbench.web.mapper;

import crm.workbench.web.entity.ActivityRemark;
import crm.workbench.web.entity.ActivityRemarkExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Mar 28 19:21:59 CST 2022
     */
    int countByExample(ActivityRemarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Mar 28 19:21:59 CST 2022
     */
    int deleteByExample(ActivityRemarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Mar 28 19:21:59 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Mar 28 19:21:59 CST 2022
     */
    int insert(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Mar 28 19:21:59 CST 2022
     */
    int insertSelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Mar 28 19:21:59 CST 2022
     */
    List<ActivityRemark> selectByExample(ActivityRemarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Mar 28 19:21:59 CST 2022
     */
    ActivityRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Mar 28 19:21:59 CST 2022
     */
    int updateByExampleSelective(@Param("record") ActivityRemark record, @Param("example") ActivityRemarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Mar 28 19:21:59 CST 2022
     */
    int updateByExample(@Param("record") ActivityRemark record, @Param("example") ActivityRemarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Mar 28 19:21:59 CST 2022
     */
    int updateByPrimaryKeySelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Mon Mar 28 19:21:59 CST 2022
     */
    int updateByPrimaryKey(ActivityRemark record);


    /**
     * ?????????????????????????????????????????????
     */
    List<ActivityRemark> selectActivityRemarkForDetailByActivityId(String id);

}
