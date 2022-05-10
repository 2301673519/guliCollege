package com.college.serviceedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.college.serviceedu.entity.vo.TeacherQuery;
import com.college.serviceutils.R;
import com.college.serviceedu.entity.EduTeacher;
import com.college.serviceedu.service.EduTeacherService;
import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zhouxiaodong
 * @since 2022-04-10
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/eduteacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAll(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("list",list);
    }

    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R deleteById(@PathVariable String id){
        boolean b = teacherService.removeById(id);
        if (b){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "分页查询讲师")
    @GetMapping("{current}/{pageSize}")
    public R pageFind(@PathVariable Long current,@PathVariable Long pageSize){
        Page page = new Page(current,pageSize);
        teacherService.page(page,null);
        List<EduTeacher> list = page.getRecords();
        Map map = new HashMap();
        map.put("Total",page.getTotal());
        map.put("list",list);
        return R.ok().data(map);
    }

    @ApiOperation(value = "条件查询分页列表")
    @PostMapping("/pageTeacherCondition/{current}/{pageSize}")
    public R pageQuery(@PathVariable Long current,
                       @PathVariable Long pageSize,
                       @RequestBody(required = false) TeacherQuery teacherQuery){
        Page page = new Page(current,pageSize);
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("gmt_create");
        if (!StringUtils.isNullOrEmpty(name)){
            queryWrapper.like("name",name);
        }
        if (level != null){
            queryWrapper.eq("level",level);
        }
        if (!StringUtils.isNullOrEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isNullOrEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }
        teacherService.page(page,queryWrapper);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        HashMap hashMap = new HashMap();
        hashMap.put("total",total);
        hashMap.put("rows",records);
        return R.ok().data(hashMap);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("insert")
    public R addTeachers(@RequestBody EduTeacher eduTeacher){
        boolean b = teacherService.save(eduTeacher);
        if (b){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "根据id查询讲师列表")
    @GetMapping("findById/{id}")
    public R findById(@PathVariable String id){
        return R.ok().data("teacher",teacherService.getById(id));
    }

    @ApiOperation(value = "修改讲师列表")
    @PostMapping("update")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",eduTeacher.getId());
        boolean b = teacherService.update(eduTeacher,queryWrapper);
        if (b){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

