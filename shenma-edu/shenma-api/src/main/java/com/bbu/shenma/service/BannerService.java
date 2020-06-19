package com.bbu.shenma.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bbu.shenma.bean.Banner;
import com.bbu.shenma.bean.Teacher;


import java.util.List;
import java.util.Map;

public interface BannerService {

    public void getBannerListByPage(IPage<Banner> iPage, Map<String,Object> map);

    public boolean addBanner(Banner banner);

    public boolean modifyBanner(Banner banner);

    public boolean removeBannerById(String id);

    List<Banner> getList(int size);

    Banner getBannerById(String id);
}
