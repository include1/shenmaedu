package com.bbu.shenma.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bbu.shenma.bean.Banner;
import com.bbu.shenma.handle.EduException;
import com.bbu.shenma.mapper.BannerMapper;
import com.bbu.shenma.service.BannerService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 使用条件进行分页查询
     * @param iPage
     * @param map
     */
    @Override
    public void getBannerListByPage(IPage<Banner> iPage, Map<String, Object> map) {

            QueryWrapper<Banner> queryWrapper = getQueryWrapper(map);
            bannerMapper.selectPage(iPage,queryWrapper);
    }

    private QueryWrapper<Banner> getQueryWrapper(Map<String, Object> map) {
        QueryWrapper<Banner> queryWrapper = new QueryWrapper<>();
        if(map != null){
            if(map.get("title") != null && !"".equals(map.get("title"))){
                queryWrapper.like("title",map.get("title"));
            }
        }
        return  queryWrapper;
    }

    /**
     * 添加数据
     * @param banner
     * @return
     */
    @Override
    public boolean addBanner(Banner banner) {
        int result = 0;
        try {
            result = bannerMapper.insert(banner);
        }catch (EduException e){
            throw new EduException(40000,"添加异常");
        }

        return result > 0;
    }

    /**
     * 更新操作
     * @param banner
     * @return
     */
    @Override
    public boolean modifyBanner(Banner banner) {
        int result = 0;
        try {
            result = bannerMapper.updateById(banner);
        }catch (EduException e){
            throw new EduException(40000,"更新异常");
        }

        return result > 0;
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @Override
    public boolean removeBannerById(String id) {
        int result = 0;
        try {
            if(!StringUtils.isBlank(id)) {
                result = bannerMapper.deleteById(id);
            }
        }catch (Exception e){
            throw new EduException(40000,"删除异常");
        }

        return result > 0;
    }

    @Override
    public List<Banner> getList(int size) {
        List<Banner> bannerList = bannerMapper.getList(size);
        return bannerList;
    }

    @Override
    public Banner getBannerById(String id) {
        if(!StringUtils.isBlank(id)) {
            return bannerMapper.selectById(id);
        }
        return  null;
    }
}
