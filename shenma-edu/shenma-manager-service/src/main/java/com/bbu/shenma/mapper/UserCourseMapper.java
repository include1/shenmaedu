package com.bbu.shenma.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bbu.shenma.bean.UserCourse;
import com.bbu.shenma.dto.UserCommentDetailDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseMapper extends BaseMapper<UserCourse> {

}
