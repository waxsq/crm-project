package crm.settings.mapper;

import crm.settings.entity.User;
import crm.settings.entity.UserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    int countByExample(UserExample example);


    int deleteByExample(UserExample example);


    int deleteByPrimaryKey(String id);


    int insert(User record);

    int insertSelective(User record);


    List<User> selectByExample(UserExample example);


    User selectByPrimaryKey(String id);


    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);


    int updateByExample(@Param("record") User record, @Param("example") UserExample example);


    int updateByPrimaryKeySelective(User record);


    int updateByPrimaryKey(User record);


    /**
     * 根据账号和密码查询用户
     */
    User selectUserByActAndPwd(Map<String,Object> map);


}
