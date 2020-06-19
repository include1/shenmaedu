package com.bbu.shenma.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bbu.shenma.bean.Banner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerMapper extends BaseMapper<Banner> {
    List<Banner> getList(@Param("size") int size);

}
