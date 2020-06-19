package com.bbu.shenma.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bbu.shenma.bean.User;
import com.bbu.shenma.client.StatisticsClient;
import com.bbu.shenma.handle.EduException;
import com.bbu.shenma.mapper.UserMapper;
import com.bbu.shenma.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StatisticsClient statisticsClient;

    @Override
    public User findUserByNameAndPwd(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isBlank(username)&&!StringUtils.isBlank(password)){
            queryWrapper.eq("username",username).
                    eq("password",password).eq("disabled",0);
            User user = userMapper.selectOne(queryWrapper);
            //判断用户不为空，更新登录数
            if(user != null && statisticsClient != null){
                //调用statistics-service服务接口
                statisticsClient.modifyStatisticsByCondition("login_num");
            }
            return  user;
        }
        return null;
    }

    @Override
    public User findUserByName(String name){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isBlank(name)){
            queryWrapper.eq("username",name);
            User user = userMapper.selectOne(queryWrapper);
            return user;
        }
        return null;
    }
    @Override
    public User findUserById(String id) {
        if(StringUtils.isNotBlank(id)){
            return userMapper.selectById(id);
        }
        return null;
    }


    @Override
    public void getPageByCondition(IPage<User> iPage, Map<String, Object> map) {
       //分页查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(map != null){
            if(StringUtils.isNotBlank((String)map.get("username"))){
                queryWrapper.like("username",map.get("username"));
            }
        }
        userMapper.selectPage(iPage,queryWrapper);
    }
    //添加
    @Override
    public boolean add(User user) {
       boolean flag = validate(user);
       if(flag){
           //为用户设置默认图片
           if(StringUtils.isBlank(user.getHeadImg())){
               user.setHeadImg("https://tvax2.sinaimg.cn/crop.72.63.297.297.50/0072FxBtly8gcp4njlq70j30dw0dwt8w.jpg?KID=imgbed,tva&Expires=1583905874&ssig=rChrjZk2XU");
           }
           int result = userMapper.insert(user);
           //更新注册数量
           if(result > 0){
               //调用statistics服务
               statisticsClient.modifyStatisticsByCondition("register_num");
           }
           return result > 0;
       }
       return false;
    }

    private boolean validate(User user) {
        if(user.getUsername() == null || "".equals(user.getUsername())){
            return false;
        }
        return  true;
    }

    //修改
    @Override
    public boolean modify(User user) {
        boolean flag = validate(user);
        if(flag){
            int result = userMapper.updateById(user);
            return result > 0;
        }
        return false;
    }
    //逻辑删除
    @Override
    public boolean removeById(String id) {
        if(!StringUtils.isBlank(id)){
            int result = userMapper.deleteById(id);
            return  result > 0;
        }
        return false;
    }

    /**
     * 获取每日注册用户的数量
     * @param day
     * @return
     */
    @Override
    public int getRegisterNum(String day) {

      int count =  userMapper.getRegisterNum(day);
      return count;
    }
    //判断是否存在微信用户
    @Override
    public User existWxUser(String openid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id",openid);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }
    //通过id查询用户部分信息，不显示密码
    @Override
    public User getUserById(String id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        queryWrapper.select("id","head_img","username","nickname","age",
                "sex","phone","sign","city","ip","open_id","disabled");
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }


}
