package com.college.serviceedu.controller;


import com.college.serviceedu.entity.chapter.ChapterVo;
import com.college.serviceedu.service.EduChapterService;
import com.college.serviceutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zhouxiaodong
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

//    创建课程大纲
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = eduChapterService.getChapterVideo(courseId);
        return R.ok().data("allChapterVideo",list);
    }

}

