package com.bbu.shenma.cotroller.front;

import com.bbu.shenma.bean.Category;
import com.bbu.shenma.service.CategoryService;
import com.bbu.shenma.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shenma/frontCategory")
@CrossOrigin
public class FrontCategoryController {
    @Autowired
    private CategoryService categoryService;

    //获取分类信息和课程信息
    @GetMapping("/getCategoryInfo")
    public Result getCategoryInfo(){
        List<Category> categoryList = categoryService.getCategoryInfo();
        return Result.ok().data("categoryList",categoryList);
    }
    //获取分类列表
    @GetMapping("/getCategoryList")
    public Result getCategoryList(){
        //获取列表
        List<Category> allCategory = categoryService.findAllCategory();
        return Result.ok().data("allCategory",allCategory);
    }
}
