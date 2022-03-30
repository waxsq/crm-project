package crm.settings.service;

import crm.settings.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: waxsq
 * @date:
 */
public interface UserService {
    User queryUserByActAndPwd(Map<String,Object> map);
    List<User> queryAllUsers();
}
