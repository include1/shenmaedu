package com.bbu.shenma.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bbu.shenma.bean.Category;
import com.bbu.shenma.bean.Teacher;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    public List<Category> findAllCategory();

    public List<Category> findCategoryById(String parentId);


    public boolean addOneCategory(Category category);

    public boolean addTwoCategory(Category category);

    public List<String> addByPoi(MultipartFile file);

    public void modify(Category category);

    public boolean removeById(String parentId);

    List<Category> getCategoryInfo();
}
