package com.bbu.shenma.cotroller;

import com.bbu.shenma.bean.Category;
import com.bbu.shenma.service.CategoryService;
import com.bbu.shenma.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/shenma/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //上传文件表格添加
    @PostMapping("/import")
    public Result addByExcel(@RequestParam("file") MultipartFile file){
        List<String> msgList = categoryService.addByPoi(file);
        return Result.ok().data("msgList",msgList);
    }
    //获取全部分类
    @GetMapping("/findAllCategory")
    public Result findAllCategory(){
        List<Category> allCategory = categoryService.findAllCategory();
        return Result.ok().data("categorys",allCategory);
    }
    //通过parentId获取分类
    @GetMapping("/getCategoryByParentId")
    public Result getCategory(String parentId){
        List<Category> list = categoryService.findCategoryById(parentId);
        return Result.ok().data("categorys",list);
    }
    //请求添加
    @PostMapping("/addOneCategory")
    public Result addOneCategory(@RequestBody Category category){
         boolean flag = categoryService.addOneCategory(category);
         if(flag){
             return Result.ok();
         }else {
             return Result.error().message("添加一级分类失败");
         }
    }
    @PostMapping("/addTwoCategory")
    public Result  addTwoCategory(@RequestBody Category category){
        boolean flag = categoryService.addTwoCategory(category);

        if(flag){
            return Result.ok();
        }else {
            return Result.error().message("添加二级分类失败");
        }
    }

    //删除
    @DeleteMapping("/delete")
    public Result delete(String id){
        boolean flag = categoryService.removeById(id);
        if(flag){
            return Result.ok();
        }else {
            return Result.error().message("存在二级分类");
        }
    }
}
