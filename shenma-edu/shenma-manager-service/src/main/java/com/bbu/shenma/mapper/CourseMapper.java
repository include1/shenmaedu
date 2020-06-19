package com.bbu.shenma.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bbu.shenma.bean.Course;
import com.bbu.shenma.dto.CourseInfoDto;
import com.bbu.shenma.dto.FrontCourseDetailInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CourseMapper extends BaseMapper<Course> {



    /**
     *  //通过id获取课程的详细信息
     * @param id
     * @return
     */
    public CourseInfoDto getCourseInfoDtoById(@Param("id") String id);

    /**
     * 分页
     * @param page
     * @param size
     * @param map
     * @return
     */

    public List<CourseInfoDto> getCourseInfoDtoByPage(@Param("page") int page, @Param("size") int size, @Param("map") Map<String,Object> map);

    /**
     * 获取前台课程详情信息
     * @param id
     * @return
     */
    public FrontCourseDetailInfoDto selectFrontCourseDetailInfo(String id);
}
