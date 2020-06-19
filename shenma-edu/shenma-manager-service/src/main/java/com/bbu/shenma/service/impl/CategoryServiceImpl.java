package com.bbu.shenma.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bbu.shenma.bean.Category;
import com.bbu.shenma.bean.Course;
import com.bbu.shenma.handle.EduException;
import com.bbu.shenma.mapper.CategoryMapper;
import com.bbu.shenma.service.CategoryService;
import com.bbu.shenma.service.CourseService;
import com.bbu.shenma.util.Result;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CourseService courseService;

    /**
     * 获取全部分类
     * @return
     */

    @Override
    public List<Category> findAllCategory() {
        List<Category> categories = new ArrayList<>();
        //获取一级分类
        List<Category> categoryById = this.findCategoryById("0");
        if(categoryById.size() > 0) {
            for (int i = 0; i < categoryById.size(); i++) {
                Category category = categoryById.get(i);
                //获取二级分类
                List<Category> subCategorys = this.findCategoryById(category.getId());
                if(subCategorys.size() > 0){
                    category.setSubCategoryList(subCategorys);
                }
                categories.add(category);
            }
        }
        return categories;
    }

    /**
     * 通过id发现类别
     * @param parentId
     * @return
     */
    @Override
    public List<Category> findCategoryById(String parentId) {
        if(!StringUtils.isBlank(parentId)) {
            QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", parentId);
            return categoryMapper.selectList(queryWrapper);
        }
        return null;
    }

    /**
     * 添加操作
     * @param category
     * @return
     */
    @Override
    public boolean addOneCategory(Category category) {
        try {
            Category exitCategory = exitCategory(category.getTitle(), "0");
            if(exitCategory == null) {
                category.setParentId("0");
                categoryMapper.insert(category);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new EduException(40000,"添加失败");
        }
    }

    @Override
    public boolean addTwoCategory(Category category) {
        try {
            Category exitCategory = exitCategory(category.getTitle(), category.getParentId());
            if(exitCategory == null) {
                categoryMapper.insert(category);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new EduException(40000,"添加失败");
        }
    }

    /**
     * 读取excel文件内容，导入数据库中（添加操作）
     * @param file
     * @return
     */
    @Override
    public List<String> addByPoi(MultipartFile file) {
        List<String> msg = new ArrayList<>();
        InputStream is = null;
        try{
        //创建输入流读取文件
            is = file.getInputStream();
        //创建workbook
            Workbook workbook  = new XSSFWorkbook(is);
        //workbook获取sheet
            Sheet sheet = workbook.getSheetAt(0);
        //进行循环遍历
            int lastRowNum = sheet.getLastRowNum();

            for(int i = 1; i <= lastRowNum; i++){
                //sheet获取row
                Row row = sheet.getRow(i);
                if(row == null){
                    String str = "表中无数据，请添加数据";
                    msg.add(str);
                    continue;
                }

                //添加一级分类
                //row 获取第一列cell
                String oneCellValue = "";
                Cell cellOne = row.getCell(0);
                if(cellOne != null){
                    oneCellValue = cellOne.getStringCellValue();
                    if("".equals(oneCellValue)){
                        String str = "第"+(i+1)+"行数据,第一列数据为空";
                        msg.add(str);
                        continue;
                    }
                }
                Category category = new Category();
                //判断该分类是否存在
                Category categoryByTitle = exitCategory(oneCellValue,"0");
                if(categoryByTitle == null&&!"".equals(oneCellValue)) {
                    category.setParentId("0");
                    category.setTitle(oneCellValue);
                    //添加数据到数据库中
                    categoryMapper.insert(category);
                    categoryByTitle = exitCategory(oneCellValue,"0");
                }
                //添加二级分类
                //row 获取第二列cell
                    String twoCellValue = "";
                    Cell cellTwo = row.getCell(1);

                    if (cellTwo != null) {
                        twoCellValue = cellTwo.getStringCellValue();
                        if ("".equals(twoCellValue)) {
                            String str = "第" + (i + 1) + "行数据,第一列数据为空";
                            msg.add(str);
                            continue;
                        }
                    }
                    //判断二级分类是否重复，并且一级分类不能为空
                    if (categoryByTitle != null&&!"".equals(oneCellValue)&&!"".equals(twoCellValue)) {
                        if (exitCategory(twoCellValue, categoryByTitle.getId()) == null) {
                            category = new Category();
                            category.setTitle(twoCellValue);
                            category.setParentId(categoryByTitle.getId());
                            //添加数据到数据库中
                            categoryMapper.insert(category);
                        }
                    }
                }
            return msg;
            }catch (Exception e){
                e.printStackTrace();
                throw new EduException(40000,"导入文件失败");
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Category exitCategory(String oneCellValue,String parentId) {
        if(!StringUtils.isBlank(oneCellValue)&&!StringUtils.isBlank(parentId)){
            QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("title",oneCellValue);
            queryWrapper.eq("parent_id",parentId);
            Category category = categoryMapper.selectOne(queryWrapper);
            return category;
        }
        return null;
    }

    /**
     * 修改操作
     * @param category
     * @return
     */

    @Override
    public void modify(Category category) {
        try {
            categoryMapper.updateById(category);
        }catch (Exception e){
            e.printStackTrace();
            throw new EduException(40000,"更新失败");
        }
    }

    @Override
    public boolean removeById(String id) {
        //首先判断是否有二级分类
        List<Category> categories = this.findCategoryById(id);
        if(categories.size() == 0) {
            int count = categoryMapper.deleteById(id);
            return count > 0;
        }
        return false;
    }
    //获取分类信息
    @Override
    public List<Category> getCategoryInfo() {
        //通过id查询父类
        List<Category> categoryList = new ArrayList<>();
        //得到了全部分类
        List<Category> allCategory = this.findAllCategory();
        //遍历分类
        for(Category category:allCategory){
            //获取子集合
            for(Category subCategory:category.getSubCategoryList()){
                    //根据子集合查询课程信息
                  Course course = courseService.getCourseByCategoryId(subCategory.getId());
                  if(course != null){
                      subCategory.setCourseName(course.getName());
                      subCategory.setCourseId(course.getId());
                  }
            }
            categoryList.add(category);
        }
        return categoryList;
    }
}
