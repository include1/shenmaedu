package com.bbu.shenma.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bbu.shenma.bean.SysUser;
import com.bbu.shenma.bean.User;

import java.util.Map;

public interface SysUserService {
    //通过用户名和密码得到一条数据

    public SysUser findSysUserByNameAndPwd(String username, String password);

    public SysUser findSysUserByName(String name);

    public SysUser fingSysUserById(String id);

    public void getPageByCondition(IPage<SysUser> iPage, Map<String,Object> map);

    public boolean add(SysUser sysUser);

    public boolean modify(SysUser sysUser);

    public boolean removeById(String id);
}
