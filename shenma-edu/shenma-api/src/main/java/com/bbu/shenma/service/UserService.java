package com.bbu.shenma.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bbu.shenma.bean.Teacher;
import com.bbu.shenma.bean.User;


import java.util.List;
import java.util.Map;

public interface UserService {
    //通过用户名和密码得到一条数据

    public User findUserByNameAndPwd(String username,String password);

    public User findUserByName(String name);

    public User findUserById(String id);

    public void getPageByCondition(IPage<User> iPage, Map<String,Object> map);

    public boolean add(User user);

    public boolean modify(User user);

    public boolean removeById(String id);

    int getRegisterNum(String day);

    User existWxUser(String openid);

    User getUserById(String id);
}
