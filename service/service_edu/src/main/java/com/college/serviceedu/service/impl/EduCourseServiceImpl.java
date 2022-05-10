package com.college.serviceedu.service.impl;

import com.college.servicebase.exceptionHandler.GuliException;
import com.college.serviceedu.entity.EduCourse;
import com.college.serviceedu.entity.EduCourseDescription;
import com.college.serviceedu.entity.vo.CourseInfoVo;
import com.college.serviceedu.mapper.EduCourseMapper;
import com.college.serviceedu.service.EduCourseDescriptionService;
import com.college.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zhouxiaodong
 * @since 2022-04-19
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
//    添加课程信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0){
            throw new GuliException(20001, "添加课程信息失败");
        }

        String cid = eduCourse.getId();

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

//    根据课程id查询基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1查询课程表类容
        EduCourse eduCourse = baseMapper.selectById(courseId);

        //封装到CourseInfoVo中
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        //2查询描述表
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

//    修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }

        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        boolean up = eduCourseDescriptionService.updateById(description);
        if (!up){
            throw new GuliException(20001, "修改课程信息失败");
        }
    }
}
