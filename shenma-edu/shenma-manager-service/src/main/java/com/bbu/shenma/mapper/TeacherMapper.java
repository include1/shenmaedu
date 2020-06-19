package com.bbu.shenma.mapper;

        import com.baomidou.mybatisplus.core.mapper.BaseMapper;
        import com.bbu.shenma.bean.Teacher;
        import org.apache.ibatis.annotations.Delete;
        import org.springframework.stereotype.Repository;

@Repository
public interface TeacherMapper extends BaseMapper<Teacher> {
    @Delete(value = "delete from tb_teacher where id = #{id} and del = 1")
    public  int deletePhysical(String id);
}
