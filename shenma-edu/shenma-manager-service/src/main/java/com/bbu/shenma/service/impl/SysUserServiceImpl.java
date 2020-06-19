package com.bbu.shenma.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bbu.shenma.bean.SysUser;
import com.bbu.shenma.mapper.SysUserMapper;
import com.bbu.shenma.service.SysUserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findSysUserByNameAndPwd(String username, String password) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isBlank(username)&&!StringUtils.isBlank(password)){
            queryWrapper.eq("username",username);
            queryWrapper.eq("password",password);
            return sysUserMapper.selectOne(queryWrapper);
        }
        return  null;
    }

    @Override
    public SysUser findSysUserByName(String name) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isBlank(name)){
            queryWrapper.eq("username",name);
            return sysUserMapper.selectOne(queryWrapper);
        }
        return null;
    }

    @Override
    public SysUser fingSysUserById(String id) {
        if(!StringUtils.isBlank(id)){
            return sysUserMapper.selectById(id);
        }
        return null;
    }

    @Override
    public void getPageByCondition(IPage<SysUser> iPage, Map<String, Object> map) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            if(map != null){
                if(!StringUtils.isBlank((String)map.get("username"))&&!"".equals((String)map.get("username"))){
                    queryWrapper.like("username",map.get("username"));
                }
            }
           sysUserMapper.selectPage(iPage,queryWrapper);
    }

    @Override
    public boolean add(SysUser sysUser) {
        boolean flag = validateSysUser(sysUser);
        if(flag){
            int result = sysUserMapper.insert(sysUser);
            return true;
        }
        return false;
    }

    private boolean validateSysUser(SysUser sysUser) {

        if("".equals(sysUser.getUsername())||sysUser.getUsername() == null){
            return false;
        }else if(sysUser.getPassword() == null || StringUtils.isBlank(sysUser.getPassword())){
            return false;
        }

        return true;
    }

    @Override
    public boolean modify(SysUser sysUser) {
        //校验用户中的字段
        boolean flag = validateSysUser(sysUser);
        if(flag){
            int result = sysUserMapper.updateById(sysUser);
            return result > 0;
        }
        return false;
    }

    @Override
    public boolean removeById(String id) {
        if (!StringUtils.isBlank(id)) {
            int result = sysUserMapper.deleteById(id);
            return result > 0;
        }
        return false;
    }
}
