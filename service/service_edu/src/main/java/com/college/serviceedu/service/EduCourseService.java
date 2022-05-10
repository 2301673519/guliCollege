package com.college.serviceedu.service;

import com.college.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.college.serviceedu.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zhouxiaodong
 * @since 2022-04-19
 */
public interface EduCourseService extends IService<EduCourse> {

//    添加课程信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);

//    根据课程id查询基本信息
    CourseInfoVo getCourseInfo(String courseId);

//    修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);
}
