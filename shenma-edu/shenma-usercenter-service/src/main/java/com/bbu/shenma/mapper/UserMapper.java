package com.bbu.shenma.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bbu.shenma.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

    int getRegisterNum(@Param("day") String day);
}
