package com.bbu.shenma.cotroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.Teacher;
import com.bbu.shenma.handle.EduException;
import com.bbu.shenma.service.TeacherService;

import com.bbu.shenma.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shenma/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/list")
    public Result list() {
        List<Teacher> list = teacherService.findList();
        return Result.ok().data("teacherList", list);
    }

    @GetMapping("/findTeacherById")
    public Result findTeacherById(String id) {
        Teacher teacher = teacherService.fingTeacherById(id);
        return Result.ok().data("record", teacher);
    }

    @GetMapping("/findListByPage")
    public Result findListByPage(int page, int size) {
        Page<Teacher> teacherPage = new Page<>(page, size);
        teacherService.findListByPage(teacherPage, null);
        return Result.ok().data("total", teacherPage.getTotal()).data("records", teacherPage.getRecords());
    }

    @PostMapping("/findPageByCondition")
    public Result findPageByCondition(int page, int size, @RequestBody(required = false) Map<String, Object> searchMap) {
        try {
            Page<Teacher> teacherPage = new Page<>(page, size);
            teacherService.getPageByCondition(teacherPage, searchMap);
            return Result.ok().data("total", teacherPage.getTotal()).data("records", teacherPage.getRecords());
        } catch (Exception e) {
            throw new EduException(40000, "这是一个自定义异常");
        }


    }

    @PostMapping("/add")
    public Result add(@RequestBody Teacher teacher) {

        teacherService.add(teacher);
        return Result.ok();

    }

    @PostMapping("/modify")
    public Result modify(@RequestBody Teacher teacher) {

        teacherService.modify(teacher);
        return Result.ok();
    }

    @DeleteMapping("/logicDel")
    public Result delete(String id) {
        boolean b = teacherService.removeById(id);
        if (b) {
            return Result.ok();
        } else {
            return Result.error().message("删除失败");
        }
    }

    @DeleteMapping("/physicalDel")
    public Result physicalDel(String id) {
        boolean b = teacherService.physicalDel(id);
        if (b) {
            return Result.ok();
        } else {
            return Result.error().message("删除失败");
        }
    }
    //批量删除
    @PostMapping("/delAllSelection")
    public Result delAllSelection(@RequestBody String [] selectionList){
        System.out.println(selectionList[0]);
        boolean b = teacherService.batchDelByIds(selectionList);
        if (b) {
            return Result.ok();
        } else {
            return Result.error().message("删除失败");
        }
    }
}
