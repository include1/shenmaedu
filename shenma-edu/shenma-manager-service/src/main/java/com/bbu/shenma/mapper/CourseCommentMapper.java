package com.bbu.shenma.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bbu.shenma.bean.CourseComment;
import com.bbu.shenma.dto.UserCommentDetailDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseCommentMapper extends BaseMapper<CourseComment> {

    //查询前台评论的详细信息（课程+讲师+评论+用户）
    public List<UserCommentDetailDto> selectUserCommentInfo(@Param(value = "uId") String uId);
}
