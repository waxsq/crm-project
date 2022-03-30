package crm.settings.service;

import crm.settings.entity.User;
import crm.settings.entity.UserExample;
import crm.settings.mapper.UserMapper;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByActAndPwd(Map<String, Object> map) {
        return userMapper.selectUserByActAndPwd(map);
    }

    /**
     * 查询所有在职的用户，要排除离职客户
     * @param
     * @return
     */
    @Override
    public List<User> queryAllUsers() {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLockStateEqualTo("1");
        return userMapper.selectByExample(userExample);
    }
}
